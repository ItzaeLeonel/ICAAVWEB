package com.sistemasmig.icaavWeb.accounting.exceptions;

public class ExistentEntityException extends Throwable {
    public ExistentEntityException(String entityType, String identity) {
        super(String.format("Un objeto de tipo [%s] con identificador [%s] ya existe", entityType, identity));
    }
    public ExistentEntityException(Class entityClass, String identity) {
        super(String.format("Un objeto de tipo [%s] con identificador [%s] ya existe", entityClass.getCanonicalName(), identity));
    }
}
