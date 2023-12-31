package com.snob.busmanagmenttool.auth.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.snob.busmanagmenttool.auth.AuthenticationRequest;
import com.snob.busmanagmenttool.auth.AuthenticationResponse;
import com.snob.busmanagmenttool.auth.RegisterRequest;
import com.snob.busmanagmenttool.exception.EmailNotConfirmedException;
import com.snob.busmanagmenttool.exception.RegistrationException;
import com.snob.busmanagmenttool.model.entity.user.BusDriver;
import com.snob.busmanagmenttool.model.entity.user.Repairman;
import com.snob.busmanagmenttool.model.entity.user.Role;
import com.snob.busmanagmenttool.model.entity.user.User;
import com.snob.busmanagmenttool.repository.user.UserRepository;
import com.snob.busmanagmenttool.token.Token;
import com.snob.busmanagmenttool.token.TokenRepository;
import com.snob.busmanagmenttool.token.TokenType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UserRepository repository;
  private final TokenRepository tokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  private final EmailConfirmationService emailConfirmationService;

  @Transactional
  public AuthenticationResponse register(RegisterRequest request) {
    if (repository.existsByEmailOrUsername(request.getEmail(),request.getUsername())){
      throw new RegistrationException("User with the same email or username already exists.");
    }
    User user;
    if (request.getRole() == Role.DRIVER) {
      user = BusDriver.builder()
              .firstname(request.getFirstname())
              .lastname(request.getLastname())
              .email(request.getEmail())
              .password(passwordEncoder.encode(request.getPassword()))
              .username(request.getUsername())
              .role(request.getRole())
              .balance(request.getBalance())
              .workExperience(request.getExperience())
              .build();
    }else if(request.getRole() == Role.REPAIRMAN){
      user = Repairman.builder()
              .firstname(request.getFirstname())
              .lastname(request.getLastname())
              .email(request.getEmail())
              .password(passwordEncoder.encode(request.getPassword()))
              .username(request.getUsername())
              .role(request.getRole())
              .workExperience(request.getExperience())
              .specialization(request.getSpecialization())
              .balance(request.getBalance())
              .build();
    }
    else {
      user =
          User.builder()
              .firstname(request.getFirstname())
              .lastname(request.getLastname())
              .email(request.getEmail())
              .password(passwordEncoder.encode(request.getPassword()))
              .username(request.getUsername())
              .balance(request.getBalance())
              .role(request.getRole())
              .build();
    }
    UUID confirmationToken = emailConfirmationService.generateConfirmationToken(user);
    var savedUser = repository.save(user);
    var jwtToken = jwtService.generateToken(user);
    var refreshToken = jwtService.generateRefreshToken(user);
    saveUserToken(savedUser, jwtToken);

    emailConfirmationService.sendConfirmationEmail(user.getEmail(), String.valueOf(confirmationToken));

    return AuthenticationResponse.builder()
            .accessToken(jwtToken)
            .refreshToken(refreshToken)
            .build();
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    request.getUsername(),
                    request.getPassword()
            )
    );
    var user = repository.findByUsername(request.getUsername())
            .orElseThrow();

    if (!user.isConfirmedByEmail()) {
      throw new EmailNotConfirmedException("Email not confirmed for this user.");
    }

    var jwtToken = jwtService.generateToken(user);
    var refreshToken = jwtService.generateRefreshToken(user);
    revokeAllUserTokens(user);
    saveUserToken(user, jwtToken);
    return AuthenticationResponse.builder()
            .accessToken(jwtToken)
            .refreshToken(refreshToken)
            .role(user.getRole())
            .build();
  }

  private void saveUserToken(User user, String jwtToken) {
    var token = Token.builder()
            .user(user)
            .token(jwtToken)
            .tokenType(TokenType.BEARER)
            .expired(false)
            .revoked(false)
            .build();
    tokenRepository.save(token);
  }

  private void revokeAllUserTokens(User user) {
    var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
    if (validUserTokens.isEmpty())
      return;
    validUserTokens.forEach(token -> {
      token.setExpired(true);
      token.setRevoked(true);
    });
    tokenRepository.saveAll(validUserTokens);
  }

  public void refreshToken(
          HttpServletRequest request,
          HttpServletResponse response
  ) throws IOException {
    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    final String refreshToken;
    final String userEmail;
    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
      return;
    }
    refreshToken = authHeader.substring(7);
    userEmail = jwtService.extractUsername(refreshToken);
    if (userEmail != null) {
      var user = this.repository.findByUsername(userEmail)
              .orElseThrow();
      if (jwtService.isTokenValid(refreshToken, user)) {
        var accessToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);
        var authResponse = AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
      }
    }
  }
}
