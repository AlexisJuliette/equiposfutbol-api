package com.unicartagena.equiposfutbol.adapter.notification;

import com.unicartagena.equiposfutbol.domain.ports.out.NotificationPort;
import com.unicartagena.equiposfutbol.domain.valueobjects.EmailAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailNotificationAdapter implements NotificationPort {

    private static final Logger log = LoggerFactory.getLogger(EmailNotificationAdapter.class);

    private final ObjectProvider<JavaMailSender> mailSenderProvider;
    private final String remitente;

    public EmailNotificationAdapter(
            ObjectProvider<JavaMailSender> mailSenderProvider,
            @Value("${app.mail.from:no-reply@equiposfutbol.app}") String remitente) {
        this.mailSenderProvider = mailSenderProvider;
        this.remitente = remitente;
    }

    @Override
    public void enviarPasswordTemporal(EmailAddress email, String username, String passwordTemporal) {
        JavaMailSender mailSender = mailSenderProvider.getIfAvailable();
        if (mailSender == null) {
            log.warn("Correo no configurado. No se envio la clave temporal al usuario {}", username);
            return;
        }

        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setFrom(remitente);
        mensaje.setTo(email.value());
        mensaje.setSubject("EquiposFutbol - Clave temporal");
        mensaje.setText("""
                Hola %s,

                Solicitaste recuperar tu contrasena.
                Tu clave temporal es: %s

                Ingresa con ella y cambiala apenas puedas.

                Si no fuiste tu, ignora este mensaje.
                """.formatted(username, passwordTemporal));

        try {
            mailSender.send(mensaje);
            log.info("Clave temporal enviada al usuario {}", username);
        } catch (Exception e) {
            log.error("Fallo el envio de la clave temporal al usuario {}", username, e);
        }
    }
}