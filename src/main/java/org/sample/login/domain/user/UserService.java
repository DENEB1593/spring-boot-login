package org.sample.login.domain.user;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.sample.login.domain.registration.token.ConfirmationToken;
import org.sample.login.domain.registration.token.ConfirmationTokenPolicy;
import org.sample.login.domain.registration.token.ConfirmationTokenService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG = "해당 이메일(%s)에 사용자가 조회되지 않습니다";

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format(USER_NOT_FOUND_MSG, email)));
    }

    public String signUpUser(User user) {

        boolean userExists = userRepository
                        .findByEmail(user.getEmail())
                        .isPresent();

        if (userExists) {
            throw new IllegalStateException("email already taken");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);

        ConfirmationToken confirmationToken = ConfirmationTokenPolicy.createToken(user);

        confirmationTokenService.saveConfirmationToken(confirmationToken);

        return confirmationToken.getToken();
    }

    public int enableUser(String email) {
        return userRepository.enableUser(email);
    }
}
