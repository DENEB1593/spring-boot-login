package org.sample.login.registration.token;

import org.sample.login.user.User;

import java.time.LocalDateTime;
import java.util.UUID;

public class ConfirmationTokenPolicy {

    public static ConfirmationToken createToken(User user) {

        String token = UUID.randomUUID().toString();

        return ConfirmationToken.builder()
                .token(token)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();
    }
}
