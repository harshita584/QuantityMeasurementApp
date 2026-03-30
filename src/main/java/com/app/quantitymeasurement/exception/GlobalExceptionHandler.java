package com.app.quantitymeasurement.exception;

import com.app.quantitymeasurement.Application;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;

class ErrorResponse{
	public LocalDateTime timeStamp;
	public int status;
	public String error;
	public String message;
	public String path;
}

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Application application;

    GlobalExceptionHandler(Application application) {
        this.application = application;
    }
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
			WebRequest request) {
		HashMap<String, String> errors = new HashMap<>();
		
		 ex.getBindingResult()
		 	.getFieldErrors()
		 	.forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
		 
		 log.info(errors.toString());
		 
		 ErrorResponse errorResponse = new ErrorResponse();
		 errorResponse.timeStamp = LocalDateTime.now();
		 errorResponse.status = HttpStatus.BAD_REQUEST.value();
		 errorResponse.error = "Quantity measurement error";
		 errorResponse.message = errors.toString();
		 errorResponse.path = request.getDescription(false).replace("uri=", "");
		 
		 return ResponseEntity.badRequest().body(errorResponse);
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex,
																	 WebRequest request) {
		log.info(ex.getMessage());

		ErrorResponse errorResponse = new ErrorResponse();

		errorResponse.timeStamp = LocalDateTime.now();
		errorResponse.status = HttpStatus.BAD_REQUEST.value();
		errorResponse.error = "Quantity measurement error";
		errorResponse.message = ex.getMessage();
		errorResponse.path = request.getDescription(false).replace("uri=", "");

		return ResponseEntity.badRequest().body(errorResponse);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException(Exception ex, WebRequest request) {
		log.info(ex.getMessage());
		ErrorResponse errorResponse = new ErrorResponse();
		
		errorResponse.timeStamp = LocalDateTime.now();
		errorResponse.status = HttpStatus.BAD_REQUEST.value();
		errorResponse.error = "Quantity measurement error";
		errorResponse.message = ex.getMessage();
		errorResponse.path = request.getDescription(false).replace("uri=", "");
		
		return ResponseEntity.badRequest().body(errorResponse);
	}
}
