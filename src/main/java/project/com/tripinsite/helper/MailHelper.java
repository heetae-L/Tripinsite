package project.com.tripinsite.helper;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class MailHelper {

	JavaMailSender mailSender;

	public MailHelper(JavaMailSender sender) {
		this.mailSender = sender;
	}

	/**
	 * 메일을 발송한다
	 * 
	 * @param sender   - 발송자 메일 주소
	 * @param receiver - 수신자 메일 주소
	 * @param subject  - 제목
	 * @param content  - 내용
	 * @throws MessagingException
	 */
	public void sendMail(String sender, String receiver, String subject, String content) throws MessagingException {

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setSubject(subject);
		helper.setText(content, true);
		helper.setFrom(new InternetAddress(sender));
		helper.setTo(new InternetAddress(receiver));
		mailSender.send(message);
	}
}
