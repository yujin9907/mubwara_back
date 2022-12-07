package site.metacoding.finals.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.metacoding.finals.dto.ResponseDto;
import site.metacoding.finals.dto.menu.MenuReqDto.MenuSaveReqDto;
import site.metacoding.finals.dto.menu.MenuRespDto.MenuSaveRespDto;
import site.metacoding.finals.service.MenuService;

@RestController
@RequiredArgsConstructor
public class MenuApiController {

    private final MenuService menuService;

    @PostMapping("/shop/menu")
    public ResponseEntity<?> joinCustomerApi(@RequestBody MenuSaveReqDto menuSaveReqDto) {

        MenuSaveRespDto respDto = menuService.save(menuSaveReqDto);

        return new ResponseEntity<>(new ResponseDto<>(HttpStatus.CREATED, "메뉴 저장", respDto), HttpStatus.CREATED);
    }
}
