import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.opussolutionsgroup.recordsbyrequest.global.Global;

public class Email {

	private String subject, recipient, message;

	/**
	 * Used to create and send an email. Send method is invoked from the
	 * constructor. A simple instantiation will result in an email being sent.
	 * 
	 * @param subject
	 *            The subject line
	 * @param recipients
	 *            The recipient of the email
	 * @param message
	 *            The message to be sent
	 */
	public Email(String subject, String recipient, String message) {

		this.subject = subject;
		this.recipient = recipient;
		this.message = message;

		sendEmail();

	}

	private void sendEmail() {

		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", Global.projectConfig.getValue("SMTP_HOST"));
		properties.put("mail.smtp.port", Global.projectConfig.getValue("SMTP_PORT"));

		Session session = Session.getInstance(properties,
				new javax.mail.Authenticator() {
					@Override
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(Global.projectConfig.getValue("EMAIL_USER"), Global.projectConfig.getValue("EMAIL_PASS"));
					}
				});

		try {

			Message email = new MimeMessage(session);
			email.setFrom(new InternetAddress(Global.projectConfig.getValue("EMAIL_USER")));
			email.setRecipient(Message.RecipientType.TO, new InternetAddress(
					recipient));
			email.setSubject(subject);
			email.setText(message);

			Transport.send(email);

		} catch (MessagingException e) {
			System.err.printf("Error sending email: %s%n", e);
		}
	}

}
