package com.gsimpson.status;

/**
 * Created by gjsimpso on 2/26/2016.
 */
public enum Return_Codes {
    STATUS_OK(0, "The request is valid"),
    STATUS_WARNING(1, "The string has been modified to be used"),
    STATUS_ERROR(2, "This string cannot be used");

    private final int id;
    private final String msg;

    Return_Codes(int id, String msg) {
        this.id = id;
        this.msg = msg;
    }

    public int getId() {
        return this.id;
    }

    public String getMsg() {
        return this.msg;
    }

}
