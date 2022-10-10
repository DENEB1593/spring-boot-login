package org.sample.login.user;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sample.login.domain.registration.token.ConfirmationToken;
import org.sample.login.domain.registration.token.ConfirmationTokenPolicy;
import org.sample.login.domain.registration.token.ConfirmationTokenService;
import org.sample.login.domain.user.User;
import org.sample.login.domain.user.UserRepository;
import org.sample.login.domain.user.UserRole;
import org.sample.login.domain.user.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.mockStatic;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Spy
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ConfirmationTokenService confirmationTokenService;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private static final String EMAIL = "test@gmail.com";

    @Test
    @DisplayName("사용자_추가_테스트")
    public void saveUserTest() {
        User user = givenUser();
        ConfirmationToken confirmationToken = givenToken(user);
        // static 추가
        MockedStatic<ConfirmationTokenPolicy> policy = mockStatic(ConfirmationTokenPolicy.class);

        given(ConfirmationTokenPolicy.createToken(user)).willReturn(confirmationToken);
        given(userRepository.findByEmail(user.getEmail())).willReturn(Optional.empty());

        // when
        String returnToken = userService.signUpUser(user);

        // verify
        Assertions.assertThat(returnToken).isEqualTo(confirmationToken.getToken());

        verify(userRepository).findByEmail(anyString());
        verify(userRepository).save(any(User.class));
    }

    private static ConfirmationToken givenToken(User user) {
        return ConfirmationTokenPolicy.createToken(user);
    }

    private static User givenUser() {
        return User.builder()
                .email(EMAIL)
                .password("1234")
                .username("kim")
                .name("park")
                .userRole(UserRole.USER)
                .build();
    }

}
