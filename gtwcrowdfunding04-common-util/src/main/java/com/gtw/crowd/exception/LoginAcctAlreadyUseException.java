package com.gtw.crowd.exception;

/**
 * @author
 * @create 2020-10-27-22:30
 */
public class LoginAcctAlreadyUseException extends RuntimeException{

    private static final long serialVersionUID = 1l;

    public LoginAcctAlreadyUseException(String message) {
        super(message);
    }
}
