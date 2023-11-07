package com.sistemasmig.icaavWeb.accounting.exceptions;

public class EntityNotExistentException extends Throwable {
    public EntityNotExistentException(String message) {
        super(message);
    }
    
    public EntityNotExistentException(Class entityClass, String identity) {
        super(String.format("El objeto de tipo [%s] con identificador [%s] no existe", entityClass.getCanonicalName(), identity));
    }
    
}
