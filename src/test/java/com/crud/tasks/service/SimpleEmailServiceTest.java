package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SimpleEmailServiceTest {

    @InjectMocks
    private SimpleEmailService simpleEmailService;

    @Mock
    private JavaMailSender javaMailSender;

    @Test
    public void shouldSendEmailWithCC() {
        //Given
        Mail mailWithCC = Mail.builder()
                .mailTo("test@test.com")
                .toCC("testCC@test.com")
                .subject("Test")
                .message("Test Message")
                .build();

        SimpleMailMessage mailMessageWithCC = new SimpleMailMessage();
        mailMessageWithCC.setTo(mailWithCC.getMailTo());
        Optional.ofNullable(mailWithCC.getToCC()).ifPresent(mailMessageWithCC::setCc);
        mailMessageWithCC.setSubject(mailWithCC.getSubject());
        mailMessageWithCC.setText(mailWithCC.getMessage());
        //When
        simpleEmailService.send(mailWithCC);
        //Then
        verify(javaMailSender, times(1)).send(mailMessageWithCC);
    }
    @Test
    public void shouldSendEmailWithoutCC() {
        //Given
        Mail mailNoCC = Mail.builder()
            .mailTo("test@test.com")
                .subject("Test")
                .message("Test Message")
                .build();

        SimpleMailMessage mailMessageNoCC = new SimpleMailMessage();
        mailMessageNoCC.setTo(mailNoCC.getMailTo());
        Optional.ofNullable(mailNoCC.getSubject());
        mailMessageNoCC.setSubject(mailNoCC.getSubject());
        mailMessageNoCC.setText(mailNoCC.getMessage());

        //When
        simpleEmailService.send(mailNoCC);
        //Then
        verify(javaMailSender, times(1)).send(mailMessageNoCC);
    }

}