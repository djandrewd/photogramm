package ua.danit.photogramm.feed.external.resources;

import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Common cases exception handler for all REST resources.
 *
 * @author Andrey Minov
 */
@ControllerAdvice
public class ExceptionsHandler extends ResponseEntityExceptionHandler {
  private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionsHandler.class);

  /**
   * Handle illegal arguments exception happens during execution.
   *
   * @param ex the exception to handle
   * @return the response entity with status code 400
   */
  @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
  public ResponseEntity<?> handleIllegalArguments(RuntimeException ex) {
    LOGGER.warn("Illegal state exception : {}", ex.getMessage());
    return ResponseEntity.badRequest().body(ex.getMessage());
  }

  /**
   * Handle constraint violation exception happens during validation of resources.
   *
   * @param ex the exception to handle
   * @return the response entity with status code 400
   */
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<?> handleConstraintViolation(ConstraintViolationException ex) {
    LOGGER.warn("Constraint exception : {}", ex.getMessage());
    String message = ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage)
                       .collect(Collectors.joining(","));
    return ResponseEntity.badRequest().body(message);
  }

  @Override
  protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body,
                                                           HttpHeaders headers, HttpStatus status,
                                                           WebRequest request) {
    LOGGER.warn("System internal exception : " + ex.getMessage(), ex);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
  }

  /**
   * Handle general error happended in the system.
   *
   * @param ex the exception to handle in the system.
   * @return the response entity with internal error (500) status code.
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<Void> handleGeneralError(Exception ex) throws Exception {
    LOGGER.warn("System internal exception : " + ex.getMessage(), ex);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
  }
}
