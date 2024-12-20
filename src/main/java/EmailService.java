import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public class EmailService {

    public static void sendNotificationEmail(String jsonFilePath, String excelFilePath, String nextTask) {

        // Placeholder email credentials
        final String fromEmail = "your_email@gmail.com"; // Replace with your email
        final String password = "your_app_password"; // Replace with your app password
        final String toEmail = "recipient_email@gmail.com"; // Replace with recipient email

        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject("Task Completion Notification");

            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setText("Task automation is complete. Please find the details attached." +
                    "Your next task is: " + nextTask);

            MimeBodyPart jsonAttachment = new MimeBodyPart();
            jsonAttachment.attachFile(jsonFilePath);

            MimeBodyPart excelAttachment = new MimeBodyPart();
            excelAttachment.attachFile(excelFilePath);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(textBodyPart);
            multipart.addBodyPart(jsonAttachment);
            multipart.addBodyPart(excelAttachment);

            message.setContent(multipart);
            Transport.send(message);
            System.out.println("Notification email sent successfully.");
        } catch (Exception e) {
            System.err.println("Exception occurred at EmailService: " + e.getMessage());
        }
    }
}