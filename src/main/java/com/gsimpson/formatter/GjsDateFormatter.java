package com.gsimpson.formatter;

import com.gsimpson.exception.GjsDateFormatException;
import com.gsimpson.status.Return_Codes;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.TreeMap;

/**
 * Created by gjsimpso on 2/26/2016.
 */
final public class GjsDateFormatter extends GjsBaseFormatter {

    public GjsDateFormatter(String pattern, String type) {
        super(pattern, type);
    }

    public Object parse(String src) {
        //You can use LocalDate.parse() or LocalDateTime.parse() on a String
        // without providing it with a pattern, if the String is in ISO-8601 format.
        // The ISO date-time formatter formats or parses a date-time without an offset, such as '2011-12-03T10:15:30'.

        DateFormat format = new SimpleDateFormat(getBasePattern());
        Date parsedDate = null;

        //try to parse the string to a LocalDate
        try {
            LocalDate ld = LocalDate.parse(src);
            LocalDateTime oneLDT = ld.atStartOfDay();
            parsedDate = Date.from(oneLDT.atZone(ZoneId.systemDefault()).toInstant());
        } catch (DateTimeParseException dtpe) {
            try {
                //try to parse the string to a LocalDateTime
                LocalDateTime ldt = LocalDateTime.parse(src);
                parsedDate = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
            } catch (DateTimeParseException dtpe2) {
                //try to parse the string using the defined pattern
                format = new SimpleDateFormat(getBasePattern());
                try {
                    parsedDate = format.parse(src);
                } catch (ParseException parseException) {
                    System.out.println("\tGJS\t  Parsing FAILED  - a Date object could not be created from " + src);
                }
            }
        }
        return parsedDate;
    }

    public String format(Object obj) {

        String returnString = null;
        try {
            if (obj instanceof Date) {
                final DateFormat format = new SimpleDateFormat(getBasePattern());
                returnString = format.format((Date) obj);
            }
        } catch (DateTimeParseException dtpe) {
            throw new GjsDateFormatException(" could not parse this date into a valid string with pattern " + getBasePattern());
        }
        return returnString;
    }

    public TreeMap<Return_Codes, String> isValid(String src) {

        //You can use LocalDate.parse() or LocalDateTime.parse() on a String
        // without providing it with a pattern, if the String is in ISO-8601 format.
        // The ISO date-time formatter formats or parses a date-time without an offset, such as '2011-12-03T10:15:30'.

        TreeMap<Return_Codes, String> validationIssues = new TreeMap<Return_Codes, String>();
        //try to parse the string to a LocalDate
        try {
            LocalDate.parse(src);
            validationIssues.put(Return_Codes.STATUS_OK, "The string is valid to parse to a Date");
        } catch (DateTimeParseException dtpe) {
            try {
                //try to parse the string to a LocalDateTime
                LocalDateTime.parse(src);
                validationIssues.put(Return_Codes.STATUS_OK, "The string is valid to parse to a Date");

            } catch (DateTimeParseException dtpe2) {
                //try to parse the string using the defined pattern
                DateFormat format = new SimpleDateFormat(getBasePattern());
                try {
                    format.parse(src);
                    validationIssues.put(Return_Codes.STATUS_OK, "The string is valid to parse to a Date using pattern " + getBasePattern());
                } catch (ParseException parseException) {
                    validationIssues.put(Return_Codes.STATUS_ERROR, src + " could not be parsed into a valid date");
                }
            }
        }
        return validationIssues;
    }
}
