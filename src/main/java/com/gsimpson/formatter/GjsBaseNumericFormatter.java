package com.gsimpson.formatter;

import com.gsimpson.exception.GjsNumericFormatException;
import com.gsimpson.status.Return_Codes;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.TreeMap;

/**
 * Created by gjsimpso on 2/26/2016.
 */

public class GjsBaseNumericFormatter extends GjsBaseFormatter {

    public GjsBaseNumericFormatter(String pattern, String type) {
        super(pattern, type);
    }

    public Object parse(String src) {
        NumberFormat nf = NumberFormat.getInstance();
        Number myNumber = null;

        try {
            myNumber = nf.parse(src);
        } catch (ParseException e) {
            // ignored we will throw a custom exception
            //e.printStackTrace();
        }
        if (myNumber != null) {
            return myNumber;
        } else {
            throw new GjsNumericFormatException("String " + src + " could not be parsed into a valid Currency.");
        }
    }

    public String format(Object obj) {

        DecimalFormat numFormat = new DecimalFormat(getBasePattern());

        NumberFormat nf = NumberFormat.getInstance();
        String returnString = null;
        if ((obj != null) && (obj instanceof Number)) {
            //returnString = nf.getInstance().format(obj);
            returnString = numFormat.format(obj);
        }
        return returnString;
    }

    public TreeMap<Return_Codes, String> isValid(String src) {
        TreeMap<Return_Codes, String> validationIssues = new TreeMap<>();

        try {
            parse(src);
            validationIssues.put(Return_Codes.STATUS_OK, src + " can be parsed into a Number");
        } catch (GjsNumericFormatException nfe) {
            validationIssues.put(Return_Codes.STATUS_ERROR, src + " could not be parsed into a valid number");
        }

        return validationIssues;
    }

}
