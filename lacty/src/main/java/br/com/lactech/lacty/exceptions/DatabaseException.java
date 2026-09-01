package br.com.lactech.lacty.exceptions;

public class DatabaseException extends RuntimeException{

    public DatabaseException(String message) {
        super(message);
    }
}
