package com.gsimpson.example;

import com.gsimpson.factory.GjsFormatterFactory;
import com.gsimpson.formatter.GjsBaseFormatter;
import com.gsimpson.formatter.GjsCurrencyFormatter;
import com.gsimpson.model.BaseEntity;
import com.gsimpson.status.Return_Codes;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.*;

/**
 * Created by gjsimpso on 2/26/2016.
 */
public class FormatShowcase {

    Locale aLocale = new Locale("en", "US");
    Locale bLocale = new Locale("fr", "CA");
    Locale cLocale = new Locale("ru", "RU");
    Locale dLocale = new Locale("en", "GB");
    Locale eLocale = new Locale("ja", "JP");

    public static void main(String[] args) {

        FormatShowcase formatController = new FormatShowcase();

        formatController.runGjsCurrencyFormatter();
        System.out.println("---------------------\n");

        formatController.runGjsDateFormatter();
        System.out.println("---------------------\n");

        formatController.runGjsEmailAddrFormatter();
        System.out.println("---------------------\n");

        formatController.runGjsEmailFormatter();
        System.out.println("---------------------\n");

        formatController.runGjsFloatFormatter();
        System.out.println("---------------------\n");

        formatController.runGjsNumberFormatter();
        System.out.println("---------------------\n");

        formatController.runGjsPhoneFormatter();
        System.out.println("---------------------\n");

        formatController.runGjsSsnFormatter();
        System.out.println("---------------------\n");
    }


    public void processOneDateTest(GjsBaseFormatter formatDate, String testString) {

        System.out.println("processing date string : " + testString);
        if (!(formatDate.getBasePattern() == null))
            System.out.println("\t\tusing pattern " + formatDate.getBasePattern());
        TreeMap<Return_Codes, String> validationResults = null;
        Date myDate = null;

        //  can testString become a valid date ?
        validationResults = formatDate.isValid(testString);

        if ((!validationResults.equals(null)) && (!validationResults.containsKey(Return_Codes.STATUS_ERROR))) {
            //if valid - parse it into a Date object
            myDate = (Date) formatDate.parse(testString);

            // if it parsed successfully, format it to the pattern
            if (!(myDate == null)) {
                System.out.println("\tFormatted Date is " + formatDate.format(myDate));
            }
        } else {

            for (Map.Entry<Return_Codes, String> entry : validationResults.entrySet()) {
                Return_Codes rc_key = entry.getKey();
                String rc_value = entry.getValue();

                System.out.println("\tisValid test results : " + rc_key + ":" + rc_value);
            }
        }
        System.out.println("\n");
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

    public void processOneNumberTest(GjsBaseFormatter formatBasePattern, String testString) {

        System.out.println("processing " + formatBasePattern.getName() + " string : " + testString);
        if (!(formatBasePattern.getBasePattern() == null))
            System.out.println("\t\tusing pattern " + formatBasePattern.getBasePattern());
        TreeMap<Return_Codes, String> validationResults = null;

        Number myNumber = null;
        validationResults = formatBasePattern.isValid(testString);

        if ((!validationResults.equals(null)) && (validationResults.containsKey(Return_Codes.STATUS_OK))) {
            myNumber = (Number) formatBasePattern.parse(testString);
            System.out.println("\tFormatted version is " + formatBasePattern.format(myNumber));
            System.out.println("\tmyNumber is " + myNumber.toString());
            //System.out.println("\tFormat        " + "default locale  : " + formatBasePattern.format(myNumber ));
            if (formatBasePattern instanceof GjsCurrencyFormatter) {
                System.out.println("\tLocale        " + aLocale.getDisplayCountry() + ": " + ((GjsCurrencyFormatter) formatBasePattern).formatWithLocale(aLocale, myNumber));
                System.out.println("\tLocale        " + bLocale.getDisplayCountry() + ": " + ((GjsCurrencyFormatter) formatBasePattern).formatWithLocale(bLocale, myNumber));
                System.out.println("\tLocale        " + cLocale.getDisplayCountry() + ": " + ((GjsCurrencyFormatter) formatBasePattern).formatWithLocale(cLocale, myNumber));
                System.out.println("\tLocale        " + dLocale.getDisplayCountry() + ": " + ((GjsCurrencyFormatter) formatBasePattern).formatWithLocale(dLocale, myNumber));
                System.out.println("\tLocale        " + eLocale.getDisplayCountry() + ": " + ((GjsCurrencyFormatter) formatBasePattern).formatWithLocale(eLocale, myNumber));
            }
        } else {
            for (Map.Entry<Return_Codes, String> entry : validationResults.entrySet()) {
                Return_Codes rc_key = entry.getKey();
                String rc_value = entry.getValue();

                System.out.println("\tisValid test results : " + rc_key + ":" + rc_value);
            }
        }

        System.out.println("\n");
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

    public void runGjsCurrencyFormatter() {
        GjsBaseFormatter formatMyCurrency = GjsFormatterFactory.getFormatter("mycurrency", null);

        List<String> testStringList = new ArrayList<String>();

        testStringList.add("1234,56,7,89.2344");
        testStringList.add("1234,56.789.2344");
        testStringList.add("a4-75.9o0b");
        testStringList.add("98765.231");
        testStringList.add("765.23");
        testStringList.add("$987,623,123.423");
        testStringList.add("98765.231,23423");
        testStringList.add("46765.231,23.423");

        for (String eachString : testStringList) {
            processOneNumberTest(formatMyCurrency, eachString);
        }

    }

    public void runGjsDateFormatter() {
        GjsBaseFormatter formatDate = GjsFormatterFactory.getFormatter("date", "yyyy/MM/dd HH:mm:ss z");
        List<String> testStringList = new ArrayList<String>();

        testStringList.add("20140903135xyz");
        testStringList.add("2014/09/03 13:59:50 MDT");
        testStringList.add("2015-08-04T10:11:30");
        testStringList.add("2011-12-03");

        for (String eachString : testStringList) {
            processOneDateTest(formatDate, eachString);
        }


        // change the date pattern on the formatter
        //  you could also create a new date formatter using a different pattern
        formatDate.setBasePattern("yyyy - MM - dd");

        //reset the testStringList
        testStringList.clear();

        testStringList.add("2012:12:03");
        testStringList.add("2012-12-33");
        testStringList.add("2013:12-03");
        testStringList.add("2012-12-03");
        testStringList.add("2012 - 12 - 03");

        for (String eachString : testStringList) {
            processOneDateTest(formatDate, eachString);
        }

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

    public void runGjsFloatFormatter() {
        GjsBaseFormatter formatFloat = GjsFormatterFactory.getFormatter("float", "##0.###");
        //"#,##0.0#;(#)");

        List<String> testStringList = new ArrayList<String>();

        testStringList.add("1234.56789");
        testStringList.add("9876.34321");
        testStringList.add("a123456789.2344");
        testStringList.add("234.5879.2344");
        testStringList.add("23,56,89.2344");

        for (String eachString : testStringList) {
            processOneNumberTest(formatFloat, eachString);
        }


        // create a new formatter pattern and rerun the same number strings
        GjsBaseFormatter formatFloat2 = GjsFormatterFactory.getFormatter("float", "0.#");
        //formatFloat.setBasePattern("##0.#");
        for (String eachString : testStringList) {
            processOneNumberTest(formatFloat2, eachString);
        }

    }

    public void runGjsNumberFormatter() {
        /*
        https://docs.oracle.com/javase/8/docs/api/java/text/NumberFormat.html
         */
        GjsBaseFormatter formatNumber = GjsFormatterFactory.getFormatter("number", "0");

        List<String> testStringList = new ArrayList<String>();

        testStringList.add("123456789");
        testStringList.add("123456789.2344");
        testStringList.add("a123456789.2344");
        testStringList.add("1234.56789.2344");
        testStringList.add("1234,56,7,89.2344");

        for (String eachString : testStringList) {
            processOneNumberTest(formatNumber, eachString);
        }

    }

    public void runGjsPhoneFormatter() {

        String US_PHONE = "\\+\\d(-\\d{3}){2}-\\d{4}";

        // industry-standard notation specified by ITU-T E.123
        String INTERNATIONAL_PHONE = "^\\+[0-9]{1,3}\\.[0-9]{4,14}(?:x.+)?$";

        GjsBaseFormatter formatPhone = GjsFormatterFactory.getFormatter("phone", US_PHONE);

        List<String> testStringList = new ArrayList<String>();

        testStringList.add("123-123-1234");
        testStringList.add("+1 123-123-1234");
        testStringList.add("+1-123-123-1234");
        testStringList.add("+1-123-123");

        for (String eachString : testStringList) {
            processOnePatternTest(formatPhone, eachString);
        }

    }

    public void runGjsSsnFormatter() {
        /*
        http://howtodoinjava.com/regex/java-regex-validate-social-security-numbers-ssn/
        ^            # Assert position at the beginning of the string.
(?!000|666)  # Assert that neither "000" nor "666" can be matched here.
[0-8]        # Match a digit between 0 and 8.
[0-9]{2}     # Match a digit, exactly two times.
-            # Match a literal "-".
(?!00)       # Assert that "00" cannot be matched here.
[0-9]{2}     # Match a digit, exactly two times.
-            # Match a literal "-".
(?!0000)     # Assert that "0000" cannot be matched here.
[0-9]{4}     # Match a digit, exactly four times.
$            # Assert position at the end of the string.
         */
        String SSN = "^(?!000|666)[0-8][0-9]{2}-(?!00)[0-9]{2}-(?!0000)[0-9]{4}$";

        GjsBaseFormatter formatSsn = GjsFormatterFactory.getFormatter("ssn", SSN);

        List<String> testStringList = new ArrayList<String>();

        testStringList.add("123-45-6789");
        testStringList.add("888-45-6789");
        testStringList.add("000-45-6789");
        testStringList.add("666-45-6789");
        testStringList.add("901-45-6789");
        testStringList.add("12-345-6789");
        testStringList.add("12-345-67891");
        testStringList.add("12345-6789");
        testStringList.add("123-45-69");

        for (String eachString : testStringList) {
            processOnePatternTest(formatSsn, eachString);
        }

    }

}
