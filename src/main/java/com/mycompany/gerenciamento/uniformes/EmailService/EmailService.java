/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciamento.uniformes.EmailService;

/**
 *
 * @author w
 */
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailService {
    private final String remetenteEmail = "wagner.jose@estudante.ifro.edu.br";
    private final String remetenteSenha = "xdbf euof abto crdy";
    private final String smtpHost = "smtp.gmail.com";
    private final String smtpPort = "587";

    public boolean enviarEmailReset(String destinatarioEmail, String codigo) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", smtpPort);

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remetenteEmail, remetenteSenha);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(remetenteEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatarioEmail));
            message.setSubject("Seu Código de Recuperação de Senha");

            String corpoEmail = "Olá,\n\n"
                              + "Você solicitou a redefinição de sua senha.\n"
                              + "Seu código de verificação é: " + codigo + "\n\n"
                              + "Este código expirará em 10 minutos.\n\n"
                              + "Se você não solicitou isso, por favor ignore este e-mail.";
            
            message.setText(corpoEmail);

            Transport.send(message);

            System.out.println("E-mail de recuperação enviado com sucesso para " + destinatarioEmail);
            return true;

        } catch (MessagingException e) {
            System.err.println("Erro ao enviar o e-mail de recuperação.");
            e.printStackTrace();
            return false;
        }
    }
}
