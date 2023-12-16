package pet.store.controller.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;
import java.util.NoSuchElementException;

import static java.util.Collections.singletonMap;

@ControllerAdvice
public class GlobalErrorHandler {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Map<String, String>> handleNoSuchElementException(NoSuchElementException e) {
        log.error("Pet store with invalid ID was not found: ", e);

        Map<String, String> responseBody = singletonMap("message", e.toString());
        return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
    }
}
