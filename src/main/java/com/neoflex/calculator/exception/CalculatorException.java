package com.neoflex.calculator.exception;

public class CalculatorException extends RuntimeException {
    private final String message;

    public CalculatorException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
