package br.com.tier.rocketleaguematchs.service.exception;

public class IntegrityViolation  extends RuntimeException{

    public IntegrityViolation(String message) {
        super (message);
    }
}
