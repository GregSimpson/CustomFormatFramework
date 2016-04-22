package com.gsimpson.formatter;

import com.gsimpson.status.Return_Codes;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.TreeMap;

/**
 * Created by gjsimpso on 2/26/2016.
 */
public class GjsCurrencyFormatter extends GjsBaseNumericFormatter {

    public GjsCurrencyFormatter(String pattern, String type) {
        super(pattern, type);
    }

    public Object parse(String src) {
        return super.parse(src);
    }

    public String formatWithLocale(Locale locale, Object obj) {
        NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
        String returnString = null;
        if ((obj != null) && (obj instanceof Number)) {
            returnString = nf.getCurrencyInstance(locale).format((Number) obj);
        }
        return returnString;
    }

    public String format(Object obj) {
        NumberFormat nf = NumberFormat.getInstance();
        String returnString = null;
        if ((obj != null) && (obj instanceof Number)) {
            returnString = nf.getInstance().format(obj);
        }
        return returnString;
    }

    public TreeMap<Return_Codes, String> isValid(String src) {
        return super.isValid(src);
    }
}
