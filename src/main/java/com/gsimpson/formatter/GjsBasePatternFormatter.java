package com.gsimpson.formatter;

import com.gsimpson.model.BaseEntity;
import com.gsimpson.status.Return_Codes;

import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by gjsimpso on 2/26/2016.
 */
public class GjsBasePatternFormatter extends GjsBaseFormatter {
    private Pattern pattern;
    protected Matcher matcher;

    private static final String DEFAULT_PATTERN = "*";

    public GjsBasePatternFormatter(String localPattern, String type) {
        super(localPattern, type);
        this.pattern = Pattern.compile(localPattern);
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(String localPattern) {
        this.pattern = Pattern.compile(localPattern);
    }

    public Object parse(String src) {

        BaseEntity myEntity = null;
        TreeMap<Return_Codes, String> validationResults = null;

        validationResults = isValid(src);
        if ((!validationResults.equals(null)) && (validationResults.containsKey(Return_Codes.STATUS_OK))) {
            myEntity = new BaseEntity(src);
        }
        return myEntity;
    }


    public String format(Object obj) {

        String returnString = null;
        if ((obj != null) && (obj instanceof BaseEntity)) {
            returnString = ((BaseEntity) obj).getValue();
        }
        return returnString;
    }

    public TreeMap<Return_Codes, String> isValid(String src) {
        matcher = pattern.matcher(src);
        TreeMap<Return_Codes, String> validationIssues = new TreeMap<>();

        if (matcher.matches()) {
            validationIssues.put(Return_Codes.STATUS_OK, "The string completely matches the pattern");
        } else if (matcher.hitEnd()) {
            validationIssues.put(Return_Codes.STATUS_WARNING, "The string is incomplete but valid so far");
        } else {
            validationIssues.put(Return_Codes.STATUS_ERROR, src + " does not match with pattern " + getBasePattern());
        }
        return validationIssues;
    }
}
