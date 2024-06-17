package de.cd.user.model.services;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import static org.junit.jupiter.api.Assertions.assertFalse;


class EmailServiceTest {

    @MockBean
    JavaMailSenderImpl javaMailSender;

    @Test
    public void testSendMail() {
        assertFalse(
                (new EmailService(javaMailSender).sendMail("Receiver", "Subject", "Text")));
    }


}