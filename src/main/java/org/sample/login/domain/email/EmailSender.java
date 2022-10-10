package org.sample.login.domain.email;

public interface EmailSender {
    public void send(String to, String email);
}
