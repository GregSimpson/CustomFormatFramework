package com.gsimpson.factory;

import com.gsimpson.formatter.*;

/**
 * Created by gjsimpso on 2/26/2016.
 */
public class GjsFormatterFactory {
    public static GjsBaseFormatter getFormatter(String type, String pattern){
        if (type.equalsIgnoreCase("BASEPATTERN")) return new GjsBasePatternFormatter(pattern, type)  ;
        else if (type.equalsIgnoreCase("DATE")) return new GjsDateFormatter(pattern, type)  ;
        else if (type.equalsIgnoreCase("EMAIL")) return new GjsEmailFormatter(pattern,type)  ;
        else if (type.equalsIgnoreCase("EMAILADDR")) return new GjsEmailAddrFormatter(pattern,type)  ;
        else if (type.equalsIgnoreCase("PHONE")) return new GjsPhoneFormatter(pattern, type)  ;
        else if (type.equalsIgnoreCase("SSN")) return new GjsSsnFormatter(pattern, type)  ;
        else if (type.equalsIgnoreCase("myCURRENCY")) return new GjsCurrencyFormatter(pattern, type)  ;
        else if (type.equalsIgnoreCase("FLOAT")) return new GjsFloatingPointNumberFormatter(pattern, type)  ;
        else if (type.equalsIgnoreCase("NUMBER")) return new GjsNumberFormatter(pattern, type)  ;

        //default
        //we will try a generic pattern formatter and hope that it works
        return new GjsBasePatternFormatter(pattern, "BasePatternDefault")  ;
    }
}
