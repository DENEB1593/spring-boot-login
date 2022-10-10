package org.sample.login.domain.registration.token;

import org.sample.login.domain.user.User;

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
