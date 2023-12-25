package com.puc.edificad.services.support.mail;

import org.thymeleaf.context.Context;

public interface EmailService {

    void sendSimpleEmail(String to, String subject, String text);

    void sendEmailWithHtmlTemplate(String to, String subject, String template, Context context);
}
