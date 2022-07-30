package org.sample.login.registration;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
public class EmailValidator implements Predicate<String> {
    @Override
    public boolean test(String s) {
        // TODO : 이메일 정규식 추가 필요
        return true;
    }
}
