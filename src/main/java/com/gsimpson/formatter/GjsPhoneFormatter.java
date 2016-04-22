package com.gsimpson.formatter;

import com.gsimpson.model.MyPhone;
import com.gsimpson.status.Return_Codes;

import java.util.TreeMap;

/**
 * Created by gjsimpso on 2/26/2016.
 */
public class GjsPhoneFormatter extends GjsBasePatternFormatter {

    String US_PHONE = "\\+\\d(-\\d{3}){2}-\\d{4}";

    // industry-standard notation specified by ITU-T E.123
    String INTERNATIONAL_PHONE = "^\\+[0-9]{1,3}\\.[0-9]{4,14}(?:x.+)?$";

    private static final String DEFAULT_PATTERN = "\\+\\d(-\\d{3}){2}-\\d{4}";

    public GjsPhoneFormatter(String localPattern, String type) {
        //super (!(localPattern == null) ? localPattern : DEFAULT_PATTERN, type);
        super(localPattern, type);
    }

    public Object parse(String src) {
        MyPhone myPhone = new MyPhone(src);
        return myPhone;
    }

    public String format(Object obj) {

        return super.format(obj);
    }

    public TreeMap<Return_Codes, String> isValid(String src) {
        return super.isValid(src);
    }

}
