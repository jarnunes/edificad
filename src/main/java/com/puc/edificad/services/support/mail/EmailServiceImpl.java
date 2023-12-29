package com.puc.edificad.services.support.mail;

import com.puc.edificad.web.config.Properties;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.UnsupportedEncodingException;

@Service
@CommonsLog
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    private final Properties properties;

    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSenderIn, TemplateEngine templateEngineIn, Properties propertiesIn) {
        this.javaMailSender = javaMailSenderIn;
        this.templateEngine = templateEngineIn;
        this.properties = propertiesIn;
    }

    @Override
    public void sendSimpleEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(properties.getEmailOrigin());
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        try {
            javaMailSender.send(message);
        } catch (Exception e) {
            log.error(e);
            throw new EmailException(e);
        }

    }

    @Override
    public void sendEmailWithHtmlTemplate(String to, String subject, String templateName, Context context) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    helper.setFrom(new InternetAddress(properties.getEmailOrigin(), properties.getApplicationName()));
                    helper.setSubject(subject);
                    helper.setTo(to);
                    String htmlContext = templateEngine.process(templateName, context);
                    helper.setText(htmlContext, true);
                    javaMailSender.send(mimeMessage);

                } catch (MessagingException | UnsupportedEncodingException e) {
                    throw new EmailException(e);
                }
            }
        }).start();
    }
}
