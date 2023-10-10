package com.snob.busmanagmenttool.auth.service;

import java.util.Optional;
import java.util.UUID;

import com.snob.busmanagmenttool.model.entity.user.User;
import com.snob.busmanagmenttool.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailConfirmationService {
    private final JavaMailSender javaMailSender;
    private final UserRepository userRepository;

    public void sendConfirmationEmail(String userEmail, String confirmationToken) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("levc4kvlad23@gmail.com");
        message.setTo(userEmail);
        message.setSubject("Confirm your registry at Trainers Collapse!");
        message.setText("Thank you for registering with Trainers Collapse!\n\n" +
                "To confirm your registration, please click on the following link:\n" +
                "http://localhost:4200/confirm?token=" + confirmationToken + "\n\n" +
                "If you didn't request this registration, please ignore this email.");
        //http://localhost:8080/api/v1/auth/confirm?token=
        javaMailSender.send(message);
    }

    public boolean confirmEmail(String confirmationToken) {
        Optional<User> userOptional = userRepository.findByConfirmationToken(UUID.fromString(confirmationToken));
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setConfirmedByEmail(true);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public UUID generateConfirmationToken(User user) {
        UUID token = UUID.randomUUID();
        user.setConfirmationToken(token);
        return token;
    }

}
