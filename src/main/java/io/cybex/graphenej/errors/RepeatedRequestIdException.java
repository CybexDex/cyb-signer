package io.cybex.graphenej.errors;


public class RepeatedRequestIdException extends Exception {
    public RepeatedRequestIdException(String message){
        super(message);
    }
}
