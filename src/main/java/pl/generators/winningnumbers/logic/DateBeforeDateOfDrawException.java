package pl.generators.winningnumbers.logic;

public class DateBeforeDateOfDrawException extends RuntimeException{
    public DateBeforeDateOfDrawException(String message) {
        super(message);
    }
}
