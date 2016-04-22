package com.gsimpson.formatter;

import com.gsimpson.model.MySsn;
import com.gsimpson.status.Return_Codes;

import java.util.TreeMap;

/**
 * Created by gjsimpso on 2/26/2016.
 */
public class GjsSsnFormatter extends GjsBasePatternFormatter {

    private static final String DEFAULT_PATTERN = "^(?!000|666)[0-8][0-9]{2}-(?!00)[0-9]{2}-(?!0000)[0-9]{4}$";

    public GjsSsnFormatter(String localPattern, String type) {
        //super (!(localPattern == null) ? localPattern : DEFAULT_PATTERN, type);
        super(localPattern, type);
    }

    public Object parse(String src) {
        MySsn mySsn = new MySsn(src);
        return mySsn;
    }

    public String format(Object obj) {
        return super.format(obj);
    }

    public TreeMap<Return_Codes, String> isValid(String src) {
        return super.isValid(src);
    }

}
