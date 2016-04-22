package com.gsimpson.example.apiUser;

import com.gsimpson.factory.GjsFormatterFactory;
import com.gsimpson.formatter.GjsBaseFormatter;
import com.gsimpson.model.BaseEntity;
import com.gsimpson.status.Return_Codes;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by gjsimpso on 2/26/2016.
 */
public class gjsEmailAPIShowcase {

    public static void main(String[] args) {

        gjsEmailAPIShowcase formatController = new gjsEmailAPIShowcase();

        formatController.runGjsEmailAddrFormatter();
        System.out.println("---------------------\n");

        formatController.runGjsEmailFormatter();
        System.out.println("---------------------\n");

    }

    private void processOneEmailTest(GjsBaseFormatter formatEmail, String testString) {
        System.out.println("processing possible email string ");

        TreeMap<Return_Codes, String> validationResults = null;

        // check string for validity
        validationResults = formatEmail.isValid(testString);
        if ((!validationResults.equals(null)) && (validationResults.containsKey(Return_Codes.STATUS_OK))) {
            System.out.println("\n\tvalid email string found ");

            //print it out
            System.out.println("\n=====>>>>>  Original message - no formatting BEGIN : \n" + testString);
            System.out.println("=====>>>>>  Original message - no formatting END   : \n");
        } else {
            // print the validation results
            for (Map.Entry<Return_Codes, String> entry : validationResults.entrySet()) {
                Return_Codes rc_key = entry.getKey();
                String rc_value = entry.getValue();
                System.out.println("\tisValid test results : " + rc_key + ":" + rc_value);

                // create an email out of it
                System.out.println("Creating a MimeMessage from the string");
                MimeMessage mimeMessage = null;
                mimeMessage = (MimeMessage) formatEmail.parse(testString);

                // just setting some example fields so they show up in the formatted version
                // a user would need to set these appropriately for their message
                System.out.println("Setting some envelope fields as examples");
                try {
                    mimeMessage.setSubject("Test Subject");
                    mimeMessage.setFrom("thisIsTheFrom@me.com");
                } catch (MessagingException e) {
                    e.printStackTrace();
                }

                //format the new message and print it
                System.out.println("\n=====>>>>>  Formatted mimeMessage BEGIN : \n" + formatEmail.format(mimeMessage));
                System.out.println("=====>>>>>  Formatted mimeMessage END : ");
            }
            System.out.println("\n\t string was : \n" + testString);
        }
        System.out.println("===================================================\n");
    }

    public void processOnePatternTest(GjsBaseFormatter formatBasePattern, String testString) {

        System.out.println("processing " + formatBasePattern.getName() + " string : " + testString);
        if (!(formatBasePattern.getBasePattern() == null))
            System.out.println("\t\tusing pattern " + formatBasePattern.getBasePattern());
        TreeMap<Return_Codes, String> validationResults = null;

        BaseEntity myBaseEntity = null;
        validationResults = formatBasePattern.isValid(testString);

        if ((!validationResults.equals(null)) && (validationResults.containsKey(Return_Codes.STATUS_OK))) {
            myBaseEntity = (BaseEntity) formatBasePattern.parse(testString);
            System.out.println("\tFormatted version is " + formatBasePattern.format(myBaseEntity));
        }
        for (Map.Entry<Return_Codes, String> entry : validationResults.entrySet()) {
            Return_Codes rc_key = entry.getKey();
            String rc_value = entry.getValue();

            System.out.println("\tisValid test results : " + rc_key + ":" + rc_value);
        }

        System.out.println("\n");
    }

    public void runGjsEmailAddrFormatter() {
        GjsBaseFormatter formatEmailAddr = GjsFormatterFactory.getFormatter("emailaddr",
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        List<String> testStringList = new ArrayList<String>();

        testStringList.add("onlyAName");
        testStringList.add("123Name@");
        testStringList.add("badDots@.com.com");
        testStringList.add("_underscore@good.com");
        testStringList.add("anotherBadDot.@good.com");
        testStringList.add("doubleDot..@good.com");
        testStringList.add("goodEmail@complete.com");
        testStringList.add("goodEmail@toolong.comx");

        for (String eachString : testStringList) {
            processOnePatternTest(formatEmailAddr, eachString);
        }

    }

    private void runGjsEmailFormatter() {
        GjsBaseFormatter formatMyCurrency = GjsFormatterFactory.getFormatter("email", null);
        String testString = null;


        //https://kb.datamotion.com/kb/wiki/30/what-does-a-sample-mime-message-look-like
        String invalidEmailExample = "MIME-Version: 1.0"
                + "X-Mailer: MailBee.NET 8.0.4.428"
                + "Subject: test subject"
                + "To: kevinm@datamotion.com"
                + "Content-Type: multipart/alternative;"
                + "boundary=\"----=_NextPart_000_AE6B_725E09AF.88B7F934\""
                + ""
                + ""
                + "------=_NextPart_000_AE6B_725E09AF.88B7F934"
                + "Content-Type: text/plain;"
                + "charset=\"utf-8\""
                + "Content-Transfer-Encoding: quoted-printable"
                + ""
                + "test body"
                + "------=_NextPart_000_AE6B_725E09AF.88B7F934"
                + "Content-Type: text/html;"
                + "charset=\"utf-8\""
                + "Content-Transfer-Encoding: quoted-printable"
                + ""
                + "<pre>test body</pre>"
                + "------=_NextPart_000_AE6B_725E09AF.88B7F934--";

        String validEmailExample =
                "Received: from toshi.toshi (/10.39.1.240)\n" +
                        "\tby default (Oracle Beehive Gateway v4.0)\n" +
                        "\twith ESMTP ; Sat, 27 Feb 2016 13:55:43 -0800\n" +
                        "Message-ID: <1456610142.14622.28.camel@oracle.com>\n" +
                        "Subject: OCNA on Linux with OBI 7.2\n" +
                        "From: Maxwell Spangler <maxwell.spangler@oracle.com>\n" +
                        "To: greg <gregory.simpson@oracle.com>\n" +
                        "Date: Sat, 27 Feb 2016 14:55:42 -0700\n" +
                        "Content-Type: multipart/alternative; boundary=\"=-47vR0uOOPSTpzf1/Ueap\"\n" +
                        "X-Mailer: Evolution 3.18.5.1 (3.18.5.1-1.fc23) \n" +
                        "Mime-Version: 1.0\n" +
                        "\n" +
                        "\n" +
                        "--=-47vR0uOOPSTpzf1/Ueap\n" +
                        "Content-Type: text/plain; charset=\"UTF-8\"\n" +
                        "Content-Transfer-Encoding: 7bit\n" +
                        "\n" +
                        "Hey, all our work was with for Linux OBI 6.x, but there is now a 7.x\n" +
                        "out.\n" +
                        "\n" +
                        "You might want to try that if you're still interested.\n" +
                        "\n" +
                        "There also appear to be people talking about it on myforums:\n" +
                        "\n" +
                        "http://myforums.oracle.com/jive3/thread.jspa?threadID=1996168&tstart=0\n" +
                        "\n" +
                        "If you have any success, let me know.. I'm not putting any effort into\n" +
                        "it right now. Too many other projects.\n" +
                        "\n" +
                        "-- \n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "  \n" +
                        "  \n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "Maxwell Spangler | DevOps Engineer\n" +
                        "\n" +
                        "Oracle Social Cloud\n" +
                        "\n" +
                        "1300 Walnut Street, Suite 200, Boulder, CO 80302\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "--=-47vR0uOOPSTpzf1/Ueap\n" +
                        "Content-Type: text/html; charset=\"utf-8\"\n" +
                        "Content-Transfer-Encoding: 7bit\n" +
                        "\n" +
                        "<html><head></head><body><div>Hey, all our work was with for Linux OBI 6.x, but there is now a 7.x out.</div><div><br></div><div>You might want to try that if you're still interested.</div><div><br></div><div>There also appear to be people talking about it on myforums:</div><div><br></div><div><a href=\"http://myforums.oracle.com/jive3/thread.jspa?threadID=1996168&amp;tstart=0\">http://myforums.oracle.com/jive3/thread.jspa?threadID=1996168&amp;tstart=0</a></div><div><br></div><div>If you have any success, let me know.. I'm not putting any effort into it right now. Too many other projects.</div><div><br></div><div><span>-- <br>\n" +
                        "\n" +
                        "\n" +
                        "  <meta http-equiv=\"Content-Type\" content=\"text/html; CHARSET=UTF-8\">\n" +
                        "  <meta name=\"GENERATOR\" content=\"GtkHTML/4.6.6\">\n" +
                        "\n" +
                        "\n" +
                        "<a href=\"http://www.oracle.com\"><img src=\"http://www.oracle.com/us/design/oracle-email-sig-198324-355094.jpg\" width=\"114\" height=\"26\" align=\"bottom\" alt=\"Oracle\" border=\"0\"></a><br>\n" +
                        "<font size=\"2\"><font color=\"#666666\">Maxwell Spangler | DevOps Engineer</font></font><br>\n" +
                        "<font size=\"2\"><font color=\"#ff0000\">Oracle</font></font><font size=\"2\"><font color=\"#666666\"> Social Cloud</font></font><br>\n" +
                        "<font size=\"2\"><font color=\"#666666\">1300 Walnut Street, Suite 200, Boulder, CO 80302</font></font>\n" +
                        "\n" +
                        "\n" +
                        "</span></div></body></html>\n" +
                        "--=-47vR0uOOPSTpzf1/Ueap--\n" +
                        "\n";

        testString = "This is the content of my email message";

        processOneEmailTest(formatMyCurrency, testString);
        processOneEmailTest(formatMyCurrency, invalidEmailExample);
        processOneEmailTest(formatMyCurrency, validEmailExample);

    }
}
