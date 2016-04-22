package com.gsimpson.exception;

/**
 * Created by gjsimpso on 2/26/2016.
 */
public class GjsNumericFormatException extends GjsBaseException {

    public GjsNumericFormatException(){
        super();
    }

    public GjsNumericFormatException(String message, Throwable cause){
        super(message, cause);
    }

    public GjsNumericFormatException(String message ){ super(message); }

    public GjsNumericFormatException(Throwable cause ){
        super(cause);
    }

}
