package com.gsimpson.formatter;

import com.gsimpson.model.MyEmail;
import com.gsimpson.status.Return_Codes;

import java.util.TreeMap;

/**
 * Created by gjsimpso on 2/26/2016.
 */
final public class GjsEmailAddrFormatter extends GjsBasePatternFormatter {

    private static final String DEFAULT_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public GjsEmailAddrFormatter(String localPattern, String type) {
        //if the pattern has not beep supplied, use the default patter for this formatter type
        //super (!(localPattern == null) ? localPattern : DEFAULT_PATTERN, type);
        super(localPattern, type);
    }

    public Object parse(String src) {
        MyEmail myEmail = null;
        TreeMap<Return_Codes, String> validationResults = null;

        validationResults = isValid(src);

        if ((!validationResults.equals(null)) && (validationResults.containsKey(Return_Codes.STATUS_OK))) {
            myEmail = new MyEmail(src);
        }
        return myEmail;
    }

    public String format(Object obj) {
        return super.format(obj);
    }

    public TreeMap<Return_Codes, String> isValid(String src) {
        return super.isValid(src);
    }

}
