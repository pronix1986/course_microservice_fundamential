package com.epam.sp.exceptions;

import com.epam.sp.models.ErrorInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorInfo> handleFileUploadException(FileUploadException e,
                                                               WebRequest request) {
        return handleGenericError(e.getMessage(), request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorInfo> handleResourceNotFoundException(ResourceNotFoundException e, WebRequest request) {
        return handleGenericError(e.getMessage(), request, HttpStatus.NOT_FOUND);
    }



    @ExceptionHandler
    public ResponseEntity<ErrorInfo> handleIllegalArgumentException(IllegalArgumentException e, WebRequest request) {
        return handleGenericError(e.getMessage(), request, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler
    public ResponseEntity<ErrorInfo> handleGenericException(Exception e, WebRequest request) {
        return handleGenericError(e.getMessage(), request, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private static ResponseEntity<ErrorInfo> handleGenericError(String e, WebRequest request, HttpStatus notFound) {
        final var errorInfo = new ErrorInfo(new Date(), e, request.getDescription(false));
        return new ResponseEntity<>(errorInfo, notFound);
    }

}
