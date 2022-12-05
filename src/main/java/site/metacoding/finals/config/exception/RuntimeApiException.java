package site.metacoding.finals.config.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class RuntimeApiException extends RuntimeException {

    private final HttpStatus httpstatus;

    public RuntimeApiException(String msg, HttpStatus httpstatus) {
        super(msg);
        this.httpstatus = httpstatus;
    }
}
