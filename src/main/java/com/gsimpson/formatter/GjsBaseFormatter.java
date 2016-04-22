package com.gsimpson.formatter;

/**
 * Created by gjsimpso on 2/26/2016.
 */

public abstract class GjsBaseFormatter implements IFormatter {
    protected String basePattern;
    protected String name;

    public GjsBaseFormatter(String basePattern, String name) {
        this.basePattern = basePattern;
        this.name = name;
    }

    public String getBasePattern() { return basePattern; }

    public void setBasePattern(String localPattern) { this.basePattern = localPattern; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
