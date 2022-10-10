package org.sample.login.domain.registration;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.sample.login.domain.user.User;
import org.sample.login.domain.user.UserRole;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequest {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;

    public User toUser() {
        return User.builder()
                .email(email)
                .username(lastName)
                .name(firstName)
                .password(password)
                .userRole(UserRole.USER)
                .build();
    }

}
