package com.antharos.bff.infrastructure.in.util;

import com.antharos.bff.domain.candidate.CandidateAlreadyRegisteredException;
import com.antharos.bff.domain.globalexceptions.AlreadyExistsException;
import com.antharos.bff.domain.globalexceptions.NotFoundException;
import com.antharos.bff.infrastructure.out.repository.exception.HiringValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler({IllegalArgumentException.class, NotFoundException.class})
  public ResponseEntity<String> handleIllegalArgumentException(RuntimeException ex) {
    log.error("IllegalArgumentException thrown: ", ex);
    return new ResponseEntity<>("Invalid request data: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(AlreadyExistsException.class)
  public ResponseEntity<String> handleAlreadyExistsException(AlreadyExistsException ex) {
    log.warn("Resource already exists exception thrown: ", ex);
    return new ResponseEntity<>("Resource already exists: " + ex.getMessage(), HttpStatus.CONFLICT);
  }

  @ExceptionHandler(CandidateAlreadyRegisteredException.class)
  public ResponseEntity<String> handleAlreadyRegistered(CandidateAlreadyRegisteredException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
  }

  @ExceptionHandler(HiringValidationException.class)
  public ResponseEntity<ErrorResponse> handleHiringValidation(HiringValidationException ex) {
    return ResponseEntity.badRequest().body(ex.getErrorResponse());
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
    log.error("Unexpected runtime exception: ", ex);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("An unexpected error occurred: " + ex.getMessage());
  }
}
