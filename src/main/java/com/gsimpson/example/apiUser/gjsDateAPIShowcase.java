package com.gsimpson.example.apiUser;

import com.gsimpson.factory.GjsFormatterFactory;
import com.gsimpson.formatter.GjsBaseFormatter;
import com.gsimpson.status.Return_Codes;

import java.util.*;

/**
 * Created by gjsimpso on 2/26/2016.
 */
public class gjsDateAPIShowcase {

    public static void main(String[] args) {

        gjsDateAPIShowcase formatController = new gjsDateAPIShowcase();

        formatController.runGjsDateFormatter();
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

    public void runGjsDateFormatter() {
        GjsBaseFormatter formatDate = GjsFormatterFactory.getFormatter("date", "yyyy/MM/dd HH:mm:ss z");
        List<String> testStringList = new ArrayList<String>();

        testStringList.add("20140903135xyz");
        testStringList.add("20140903135");
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

}
