package com.snob.busmanagmenttool.auth.controller;

import com.snob.busmanagmenttool.auth.AuthenticationRequest;
import com.snob.busmanagmenttool.auth.AuthenticationResponse;
import com.snob.busmanagmenttool.auth.RegisterRequest;
import com.snob.busmanagmenttool.auth.service.AuthenticationService;
import com.snob.busmanagmenttool.auth.service.EmailConfirmationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The AuthenticationController handles user authentication and token management.
 */
@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class AuthenticationController {
  private final AuthenticationService service;
  private final EmailConfirmationService emailConfirmationService;

  /**
     * Register a new user.
     *
     * @param request The registration request containing user details.
     * @return ResponseEntity with an AuthenticationResponse upon successful registration.
     */
  @PostMapping("/register")
  public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
    return ResponseEntity.ok(service.register(request));
  }

    /**
     * Authenticate a user.
     *
     * @param request The authentication request containing user credentials.
     * @return ResponseEntity with an AuthenticationResponse upon successful authentication.
     */
  @PostMapping("/authenticate")
  public ResponseEntity<AuthenticationResponse> authenticate(
      @RequestBody AuthenticationRequest request) {
    return ResponseEntity.ok(service.authenticate(request));
  }

    /**
     * Refresh the authentication token.
     *
     * @param request  The HTTP request containing the current authentication token.
     * @param response The HTTP response where the new token will be sent.
     * @throws IOException If an IO error occurs during token refresh.
     */
  @PostMapping("/refresh-token")
  public void refreshToken(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    service.refreshToken(request, response);
  }
  /**
   * Confirm user's email using a confirmation token.
   *
   * @param confirmationToken The confirmation token received via email.
   * @return ResponseEntity with a message indicating the status of email confirmation.
   */
  @GetMapping("/confirm")
  public ResponseEntity<String> confirmEmail(@RequestParam("token") String confirmationToken) {
    boolean confirmed = emailConfirmationService.confirmEmail(confirmationToken);
    return confirmed
            ? ResponseEntity.ok("Email confirmed")
            : ResponseEntity.badRequest().body("Bad confirmation token");
  }
}
