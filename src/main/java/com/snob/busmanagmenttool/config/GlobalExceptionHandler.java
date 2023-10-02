package com.snob.busmanagmenttool.config;

import com.snob.busmanagmenttool.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<String> handleUserNotFoundException(EntityNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
  }

  @ExceptionHandler(DriverAlreadyHasBusException.class)
  public ResponseEntity<String> handleDriverAlreadyHasBusException(
      DriverAlreadyHasBusException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
  }

  @ExceptionHandler(BusSeatIsAlreadyTaken.class)
  public ResponseEntity<String> handleBusSeatsAlreadyTaken(Exception ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
  }

  @ExceptionHandler(BusAlreadyHasRouteException.class)
  public ResponseEntity<String> handleBusAlreadyHasRouteException(BusAlreadyHasRouteException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
  }

  @ExceptionHandler(UserIsNotDriverException.class)
  public ResponseEntity<String> handleUserIsNotDriverException(UserIsNotDriverException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
  }

  @ExceptionHandler(RepairDocumentAlreadyExists.class)
  public ResponseEntity<String> handleRepairDocumentAlreadyExists(RepairDocumentAlreadyExists ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleException() {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("An internal server error occurred");
  }
}
