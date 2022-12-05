package site.metacoding.finals.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;
import site.metacoding.finals.config.exception.RuntimeApiException;
import site.metacoding.finals.dto.ResponseDto;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(RuntimeApiException.class)
    public ResponseEntity<?> globalException(RuntimeApiException e) {
        log.debug("디버그 : Exception advice 실행");
        return new ResponseEntity<>(new ResponseDto<>(e.getHttpstatus(), e.getMessage(), null), e.getHttpstatus());
    }
}
