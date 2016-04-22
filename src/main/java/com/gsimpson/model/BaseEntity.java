package com.gsimpson.model;

/**
 * Created by gjsimpso on 2/26/2016.
 */
public class BaseEntity implements IEntity{

    public String value = null;

    public BaseEntity(String value) { this.setValue(value); }

    public String asString(){
        return (value);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
