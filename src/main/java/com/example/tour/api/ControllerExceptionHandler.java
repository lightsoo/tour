package com.example.tour.api;

import com.example.tour.exception.NotFoundProgramException;
import com.example.tour.exception.NotFoundServiceRegionException;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@Slf4j
//@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = {
        NotFoundProgramException.class,
        NotFoundServiceRegionException.class
    })
    public ResponseEntity<ErrorDTO> notFoundException(Exception ex, WebRequest rew) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMessage(ex.getLocalizedMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(errorDTO);
    }

    @ExceptionHandler(value = {
        MethodArgumentNotValidException.class
    })
    public ResponseEntity<ErrorDTO> badRequestException(Exception ex, WebRequest rew) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMessage(ex.getLocalizedMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(errorDTO);
    }

    @ExceptionHandler(value = {
        Exception.class
    })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorDTO> unknownException(Exception ex, WebRequest req) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMessage(ex.getLocalizedMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body(errorDTO);
    }

    @Data
    @NoArgsConstructor
    private class ErrorDTO {
        private String message;
    }

}
