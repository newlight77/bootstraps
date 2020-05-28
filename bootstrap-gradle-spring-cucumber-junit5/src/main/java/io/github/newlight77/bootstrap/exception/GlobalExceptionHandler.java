package io.github.newlight77.bootstrap.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
        NotAllowedException.class
    })
    public Object handle400(HttpServletRequest request, Exception ex) {
        return new ExceptionDetail(
                ex.getClass().getName(), 
                LocalDateTime.now().toString(),
                ex.getMessage(),
                request.getRequestURI(),
                request.getQueryString());
    }

}
