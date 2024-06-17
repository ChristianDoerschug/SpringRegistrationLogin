package de.cd.user.model.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * EmailService class
 */
@Service
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Value("${email.noreplysender}")
    private String noReplySender;

    /**
     * Method to send an email
     *
     * @param receiver Where is the email going to be send to
     * @param subject  The subject of the email
     * @param text     The content of the email
     * @return Boolean
     */
    public Boolean sendMail(String receiver, String subject, String text) {
        try {
            LOGGER.info("Try to send email");
            SimpleMailMessage email = new SimpleMailMessage();
            email.setFrom(noReplySender);
            email.setTo(receiver);
            email.setSubject(subject);
            email.setText(text);
            javaMailSender.send(email);
            LOGGER.info("Email with subject " + subject + " has been sent to: " + receiver);
            return true;
        } catch (Exception e) {
            LOGGER.warn("Email with " + subject + " has not been sent to: " + receiver);
            return false;
        }
    }
}
