package com.gsimpson.formatter;

import com.gsimpson.status.Return_Codes;

import java.util.TreeMap;

/**
 * Created by gjsimpso on 2/26/2016.
 */
public interface IFormatter {
    Object parse(String src);
    String format(Object obj);
    TreeMap<Return_Codes, String> isValid(String src);
}
