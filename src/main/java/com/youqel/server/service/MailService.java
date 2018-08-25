package com.youqel.server.service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.youqel.server.domain.PickupRequestEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MailService {

    @Autowired
    private JavaMailSender sender;

    @Autowired
    private VelocityEngine velocityEngine;

    private boolean sendHtmlFormattedEmail(final String to, final String from, final String subject,
            final String emailBody) {

        return sendEmail(Lists.newArrayList(to), from, subject, emailBody);
    }

    private boolean sendEmail(final List<String> to, final String from, final String subject,
            final String emailBody) {

        final MimeMessagePreparator preparator = mimeMessage -> {
            final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
            message.setTo(to.toArray(new String[to.size()]));
            message.setFrom(from);
            message.setSubject(subject);
            message.setText(extractPlainText(emailBody), emailBody);
        };

        try {
            sender.send(preparator);
            log.info("Sent email to {} from {}", to, from);
            return true;
        } catch (final MailSendException e) {
            log.warn("Could not send Email. Maybe mail server is down.");
            return false;
        }
    }

    /**
     * Extracts plain text from input html text.
     *
     * @param htmlText
     * @return
     */
    private String extractPlainText(final String htmlText) {
        return htmlText.replaceAll("\\<.*?\\>", "");
    }

    public boolean sendNewPickupRequestNotificaiton(final PickupRequestEntity request) {
        final Map<String, Object> model = Maps.newHashMap();
        model.put("request", request);
        model.put("pickupDate",
                new SimpleDateFormat("yyyy-mm-dd hh:mm").format(request.getPickupDate()));

        final String emailBody = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
                "velocity/pickup-request-notification.vm", "UTF-8", model);

        log.info(" Sending preferences email to {} ", "");

        return sendHtmlFormattedEmail("youqel.app@gmail.com", "no-reply@youqel.com", "New Pickup request", emailBody);

    }

}
