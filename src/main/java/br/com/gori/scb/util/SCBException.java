package br.com.gori.scb.util;

/**
 *
 * @author Leonardo
 */
public class SCBException extends RuntimeException {

    public SCBException(String message) {
        super(message);
    }

    public SCBException(String message, Throwable cause) {
        super(message, cause);
    }

}
