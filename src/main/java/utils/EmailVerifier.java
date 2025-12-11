package utils;

import java.io.IOException;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.MimeMultipart;

public class EmailVerifier {
    private final String host;
    private final String username;
    private final String password;
    private final int port;

    // ✅ Constructor
    public EmailVerifier(String host, int port, String username, String password) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    /** ✅ Waits for an email with a matching subject/body */
    public Message waitForEmail(String subjectKeyword, String bodyKeyword,
                                int pollingIntervalSeconds, int maxAttempts) throws Exception {
        int attempts = 0;
        while (attempts++ < maxAttempts) {
            Message m = findLatestBySubjectOrBody(subjectKeyword, bodyKeyword);
            if (m != null) return m;
            Thread.sleep(pollingIntervalSeconds * 1000L);
        }
        return null;
    }

    /** ✅ Searches latest messages for a keyword in subject/body */
    private Message findLatestBySubjectOrBody(String subjectKeyword, String bodyKeyword) throws Exception {
        Properties props = new Properties();
        props.put("mail.store.protocol", "imaps");
        Session session = Session.getInstance(props);

        try (Store store = session.getStore("imaps")) {
            store.connect(host, username, password);
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            Message[] messages = inbox.getMessages();
            for (int i = messages.length - 1; i >= 0; i--) {
                Message msg = messages[i];
                String subject = msg.getSubject() == null ? "" : msg.getSubject();

                // ✅ Check subject match
                boolean subjectMatch = (subjectKeyword == null)
                        || subject.toLowerCase().contains(subjectKeyword.toLowerCase());

                // ✅ Check body match
                if (subjectMatch) {
                    if (bodyKeyword == null || bodyKeyword.isEmpty()) {
                        return msg;
                    } else {
                        String body = getTextFromMessage(msg);
                        if (body != null && body.toLowerCase().contains(bodyKeyword.toLowerCase())) {
                            return msg;
                        }
                    }
                }
            }
        }
        return null;
    }

    /** ✅ Extracts readable text from message body */
    public String getTextFromMessage(Message message) throws MessagingException, IOException {
        Object content = message.getContent();
        if (content instanceof String) {
            return (String) content;
        } else if (content instanceof MimeMultipart) {
            MimeMultipart mp = (MimeMultipart) content;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mp.getCount(); i++) {
                BodyPart bp = mp.getBodyPart(i);
                Object partContent = bp.getContent();
                if (partContent instanceof String) {
                    sb.append((String) partContent);
                }
            }
            return sb.toString();
        }
        return null;
    }
}
