package com.gsimpson.exception;

/**
 * Created by gjsimpso on 2/26/2016.
 */
public class GjsDateFormatException extends GjsBaseException {

    public GjsDateFormatException(){
        super();
    }

    public GjsDateFormatException(String message, Throwable cause){
        super(message, cause);
    }

    public GjsDateFormatException(String message ){
        super(message);
    }

    public GjsDateFormatException(Throwable cause ){
        super(cause);
    }

}
