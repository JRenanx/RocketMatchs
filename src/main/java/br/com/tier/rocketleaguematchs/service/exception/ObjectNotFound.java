package br.com.tier.rocketleaguematchs.service.exception;

public class ObjectNotFound extends RuntimeException{

    public ObjectNotFound (String message) {
        super(message);
    }
}
