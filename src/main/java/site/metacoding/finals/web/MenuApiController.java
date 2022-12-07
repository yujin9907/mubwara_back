package site.metacoding.finals.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.metacoding.finals.dto.ResponseDto;
import site.metacoding.finals.dto.customer.CustomerReqDto.CustomerJoinReqDto;
import site.metacoding.finals.dto.menu.MenuReqDto.MenuSaveReqDto;

@RestController
@RequiredArgsConstructor
public class MenuApiController {

    @PostMapping("/auth/shop/menu")
    public ResponseEntity<?> joinCustomerApi(@RequestBody MenuSaveReqDto menuSaveReqDto) {

        return new ResponseEntity<>(new ResponseDto<>(HttpStatus.CREATED, "메뉴 저장", null), HttpStatus.CREATED);
    }
}
