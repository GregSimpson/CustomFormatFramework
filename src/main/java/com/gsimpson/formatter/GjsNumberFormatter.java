package com.gsimpson.formatter;

import com.gsimpson.status.Return_Codes;

import java.util.TreeMap;

/**
 * Created by gjsimpso on 2/26/2016.
 */
public class GjsNumberFormatter extends GjsBaseNumericFormatter {

    public String DEFAULT_PATTERN = "0";

    public GjsNumberFormatter(String pattern, String type) {
        super(pattern, type);
    }

    public Object parse(String src) {
        return super.parse(src);
    }

    public String format(Object obj) {
        return super.format(obj);
    }

    public TreeMap<Return_Codes, String> isValid(String src) {
        return super.isValid(src);
    }
}
