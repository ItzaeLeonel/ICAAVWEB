/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistemasmig.icaavWeb.accounting.exceptions.handler;

import com.mysql.cj.exceptions.CJException;
import com.sistemasmig.icaavWeb.accounting.exceptions.BadRequestException;
import com.sistemasmig.icaavWeb.accounting.exceptions.BusinessLogicException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotExistentException;
import com.sistemasmig.icaavWeb.accounting.exceptions.ExistentEntityException;
import com.sistemasmig.icaavWeb.accounting.exceptions.NoAccessGrantedException;
import com.sistemasmig.icaavWeb.accounting.exceptions.handler.model.ErrorDetails;
import java.util.Date;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 *
 * @author Waldir.Valle
 */
@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(ExistentEntityException.class)
  public final ResponseEntity<ErrorDetails> handleExistentEntityException(ExistentEntityException ex, WebRequest request) {
    ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
        "ExistentEntityException");
    return new ResponseEntity<>(errorDetails, HttpStatus.ALREADY_REPORTED);
  }
  
  @ExceptionHandler(BusinessLogicException.class)
  public final ResponseEntity<ErrorDetails> handleBusinessLogicException(BusinessLogicException ex, WebRequest request) {
      //System.out.println("CustomizedResponseEntityExceptionHandler ex.getMessage()=>"+ex.getMessage());
    ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
        "BusinessLogicException");
    return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
  }
  
  @ExceptionHandler(EntityNotExistentException.class)
  public final ResponseEntity<ErrorDetails> handleEntityNotExistentException(EntityNotExistentException ex, WebRequest request) {
      //System.out.println("CustomizedResponseEntityExceptionHandler ex.getMessage()=>"+ex.getMessage());
    ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
        "EntityNotExistentException");
    return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
  }
  
  @ExceptionHandler(NoAccessGrantedException.class)
  public final ResponseEntity<ErrorDetails> handleNoAccessGrantedException(NoAccessGrantedException ex, WebRequest request) {
      //System.out.println("CustomizedResponseEntityExceptionHandler ex.getMessage()=>"+ex.getMessage());
    ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
        "NoAccessGrantedException");
    return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
  }
  
  @ExceptionHandler(CJException.class)
  public final ResponseEntity<ErrorDetails> handleCJException(CJException ex, WebRequest request) {
      //System.out.println("CustomizedResponseEntityExceptionHandler ex.getMessage()=>"+ex.getMessage());
    ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
        "CJException");
    return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
  }
  
  @ExceptionHandler(BadRequestException.class)
  public final ResponseEntity<ErrorDetails> handleBadRequestException(BadRequestException ex, WebRequest request) {
      System.out.println("CustomizedResponseEntityExceptionHandler ex.getMessage()=>"+ex.getMessage());
    ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
        "BadRequestException");
    return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
  }
  
   @Override
     protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    
         System.out.println("CustomizedResponseEntityExceptionHandler ex.getMessage()=>"+ex.getMessage());
    ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
        "BadRequestException");
    return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
  }
}
