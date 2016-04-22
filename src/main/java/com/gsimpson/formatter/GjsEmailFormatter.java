package com.gsimpson.formatter;

import com.gsimpson.status.Return_Codes;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;


/**
 * Created by gjsimpso on 2/28/2016.
 */
public class GjsEmailFormatter extends GjsBaseFormatter {
    public GjsEmailFormatter(String basePattern, String name) {
        super(basePattern, name);
    }

    @Override
    public Object parse(String src) {

        //http://docs.spring.io/spring/docs/4.0.x/spring-framework-reference/html/mail.html
        //  http://www.programcreek.com/java-api-examples/javax.mail.internet.MimeMessage ???
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setText(src);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return message;
    }


    /*
   * This method would print FROM,TO and SUBJECT of the message
   */
    //public static void writeEnvelope(Message m) throws Exception {
    public String getEnvelope(Message m) throws Exception {
        StringBuffer returnSB = new StringBuffer();

        returnSB.append("This is the message envelope\n");
        returnSB.append("---------------------------\n");

        Address[] a;

        // FROM
        if ((a = m.getFrom()) != null) {
            for (int j = 0; j < a.length; j++) {
                returnSB.append("FROM: " + a[j].toString() + "\n");
            }
        }

        // TO
        if ((a = m.getRecipients(Message.RecipientType.TO)) != null) {
            for (int j = 0; j < a.length; j++) {
                returnSB.append("TO: " + a[j].toString() + "\n");
            }
        }

        // SUBJECT
        if (m.getSubject() != null) {
            returnSB.append("SUBJECT: " + m.getSubject() + "\n");
        }
        return returnSB.toString();
    }

    /*
* This method checks for content-type
* based on which, it processes and
* fetches the content of the message
* */
    //public String format(Object obj) throws Exception {
    public String format(Object obj) {
        // http://www.tutorialspoint.com/javamail_api/javamail_api_quick_guide.htm

        StringBuffer returnSB = new StringBuffer();

        if (obj instanceof Message) {
            //Call method writeEnvelope
            try {
                returnSB.append(getEnvelope((Message) obj));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (obj instanceof Part) {
            Part p = (Part) obj;
            returnSB.append("----------------------------" + "\n");
            try {
                returnSB.append("CONTENT-TYPE: " + p.getContentType() + "\n");

                //check if the content is plain text
                if (p.isMimeType("text/plain")) {
                    returnSB.append("This is plain text" + "\n");
                    returnSB.append("---------------------------" + "\n");
                    returnSB.append((String) p.getContent() + "\n");
                }
                //check if the content has attachment
                else if (p.isMimeType("multipart/*")) {
                    returnSB.append("This is a Multipart" + "\n");
                    returnSB.append("---------------------------" + "\n");
                    Multipart mp = (Multipart) p.getContent();
                    int count = mp.getCount();
                    for (int i = 0; i < count; i++)
                        returnSB.append(format(mp.getBodyPart(i)) + "\n");
                }
                //check if the content is a nested message
                else if (p.isMimeType("message/rfc822")) {
                    returnSB.append("This is a Nested Message" + "\n");
                    returnSB.append("---------------------------" + "\n");
                    returnSB.append(format((Part) p.getContent()) + "\n");
                }
                //check if the content is an inline image
                else if (p.isMimeType("image/jpeg")) {
                    returnSB.append("--------> image/jpeg" + "\n");
                    Object o = p.getContent();

                    InputStream x = (InputStream) o;
                    // Construct the required byte array
                    returnSB.append("x.length = " + x.available() + "\n");
                    int i = 0;
                    byte[] bArray = new byte[x.available()];

                    while ((i = (int) ((InputStream) x).available()) > 0) {
                        int result = (int) (((InputStream) x).read(bArray));
                        if (result == -1)
                            break;
                    }
                    FileOutputStream f2 = new FileOutputStream("/tmp/image.jpg");
                    f2.write(bArray);
                } else if (p.getContentType().contains("image/")) {
                    returnSB.append("content type" + p.getContentType());
                    File f = new File("image" + new Date().getTime() + ".jpg");
                    DataOutputStream output = new DataOutputStream(
                            new BufferedOutputStream(new FileOutputStream(f)));
                    com.sun.mail.util.BASE64DecoderStream test =
                            (com.sun.mail.util.BASE64DecoderStream) p.getContent();
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = test.read(buffer)) != -1) {
                        output.write(buffer, 0, bytesRead);
                    }
                } else {
                    Object o = p.getContent();
                    if (o instanceof String) {
                        returnSB.append("This is a string" + "\n");
                        returnSB.append("---------------------------" + "\n");
                        returnSB.append((String) o + "\n");
                    } else if (o instanceof InputStream) {
                        returnSB.append("This is just an input stream" + "\n");
                        returnSB.append("---------------------------" + "\n");
                        InputStream is = (InputStream) o;
                        is = (InputStream) o;
                        int c;
                        while ((c = is.read()) != -1)
                            returnSB.append(c);
                    } else {
                        returnSB.append("This is an unknown type" + "\n");
                        returnSB.append("---------------------------" + "\n");
                        returnSB.append(o.toString() + "\n");
                    }
                }
            } catch (MessagingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return returnSB.toString();
    }


    //@Override
    public String firstFormatormat(Object obj) {

        MimeMessage mimeMessage = null;
        if (obj instanceof MimeMessage) {
            mimeMessage = (MimeMessage) obj;

            try {
                return mimeMessage.getContent().toString();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public TreeMap<Return_Codes, String> isValid(String src) {

        System.out.println("\temail isvalid : checking candidate string : ");

        TreeMap<Return_Codes, String> validationIssues = new TreeMap<>();

        List<String> emailKeywords = new ArrayList<String>();

        emailKeywords.add("Received");
        emailKeywords.add("Message-ID");
        emailKeywords.add("Subject");
        emailKeywords.add("From");
        emailKeywords.add("To");
        emailKeywords.add("Date");
        emailKeywords.add("Content-Type");
        emailKeywords.add("MIME-Version");
        emailKeywords.add("NextPart");
        //emailKeywords.add("notOneOfThese");
        //emailKeywords.add("norOneOfThese");
        //emailKeywords.add("neverThis");

        if (src == null || "".equals(src)) {
            validationIssues.put(Return_Codes.STATUS_OK, "This is a valid email string");
        } else {
            int countHits = 0;
            for (String eachWord : emailKeywords) {
                if (src.contains(eachWord)) {
                    countHits++;
                }
            }
            float percentage = (((countHits * 100.0f) / emailKeywords.size()));

            //System.out.println(" countHits  : " + countHits);
            //System.out.println(" out of     : " + emailKeywords.size());
            System.out.println("\t\tpercentage : " + percentage);

            if (percentage > 70) {
                validationIssues.put(Return_Codes.STATUS_OK, "This is a valid email string");
            } else if (percentage > 50) {
                validationIssues.put(Return_Codes.STATUS_WARNING, "This could be a valid email string");
            } else {
                validationIssues.put(Return_Codes.STATUS_ERROR, "This is not a valid email string");
            }
        }
        return validationIssues;
    }
}
