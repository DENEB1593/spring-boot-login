package org.sample.login.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@Import({UserRepository.class})
public class UserRepositoryTest {

    @MockBean
    private UserRepository userRepository;

    private static final String TEST_EMAIL = "test@gmail.com";
    private static final int UPDATE_SUCCESS = 1;

    @Test
    @DisplayName("사용자_조회_테스트")
    public void userSelectTest() {

        User user = User.builder()
                .email(TEST_EMAIL)
                .password("1234")
                .username("kim")
                .name("park")
                .userRole(UserRole.USER)
                .build();

        when(userRepository.findByEmail(TEST_EMAIL))
                .thenReturn(Optional.of(user));

        User selectedUser = userRepository.findByEmail(TEST_EMAIL).orElse(null);

        Assertions.assertEquals(TEST_EMAIL, selectedUser.getEmail());
    }

    @Test
    @DisplayName("사용자_추가_테스트")
    public void userSaveTest() {
        when(userRepository.save(any(User.class)))
                .then(returnsFirstArg());


        User user = User.builder()
                .email(TEST_EMAIL)
                .password("1234")
                .username("kim")
                .name("park")
                .userRole(UserRole.USER)
                .build();

        User savedUser = userRepository.save(user);

        Assertions.assertEquals(TEST_EMAIL, savedUser.getEmail());
        Assertions.assertEquals("1234", savedUser.getPassword());

        verify(userRepository).save(any());
    }

    @Test
    @DisplayName("사용자_인증여부_수정_테스트")
    public void userEnableTest() {
        when(userRepository.enableUser(TEST_EMAIL))
                .thenReturn(UPDATE_SUCCESS);

        int result = userRepository.enableUser(TEST_EMAIL);

        Assertions.assertEquals(UPDATE_SUCCESS, result);
    }
}