package site.metacoding.finals.dto;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@Getter
public class ResponseDto<T> {
    private HttpStatus code; // http 상태코드
    private String msg;
    private T data;
}
