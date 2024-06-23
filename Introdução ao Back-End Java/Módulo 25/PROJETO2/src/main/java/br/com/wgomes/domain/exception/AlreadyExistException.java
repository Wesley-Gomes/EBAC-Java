package br.com.wgomes.domain.exception;

public class AlreadyExistException extends Exception {
    public AlreadyExistException(String message) {
        super(message);
    }
}
