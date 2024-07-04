package br.com.wgomes.domain.exception;

public class CannotChangedException extends Exception{
    public CannotChangedException(String message) {
        super(message);
    }
}
