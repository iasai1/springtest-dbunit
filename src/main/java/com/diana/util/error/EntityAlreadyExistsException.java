package com.diana.util.error;

public class EntityAlreadyExistsException extends RuntimeException {
    public EntityAlreadyExistsException(String message){
        super(message);
    }
}
