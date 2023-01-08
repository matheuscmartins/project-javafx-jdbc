package com.udemy.projectjavafxjdbc.model.exceptions;

import java.util.HashMap;
import java.util.Map;

//classe para tratar os erros de validação de campo
public class ValidationException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    private Map<String, String> errors = new HashMap<>();

    public ValidationException(String msg){
        super(msg);
    }
    public Map<String, String> getErrors(){
        return  errors;
    }
    public void addError(String fieldName, String errorMessage){
        errors.put(fieldName, errorMessage);
    }
}
