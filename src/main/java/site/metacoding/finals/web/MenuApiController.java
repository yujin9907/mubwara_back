package site.metacoding.finals.web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.metacoding.finals.config.annotation.VerifyShop;
import site.metacoding.finals.config.auth.PrincipalUser;
import site.metacoding.finals.dto.ResponseDto;
import site.metacoding.finals.dto.menu.MenuReqDto.MenuSaveReqDto;
import site.metacoding.finals.dto.menu.MenuRespDto.MenuListRespDto;
import site.metacoding.finals.dto.menu.MenuRespDto.MenuSaveRespDto;
import site.metacoding.finals.service.MenuService;

@RestController
@RequiredArgsConstructor
public class MenuApiController {

    private final MenuService menuService;

    @VerifyShop
    @GetMapping("/shop/menu")
    public ResponseEntity<?> viewMenu(
            @AuthenticationPrincipal PrincipalUser principalUser) {
        List<MenuListRespDto> respDtos = menuService.list(principalUser);

        return new ResponseEntity<>(new ResponseDto<>(HttpStatus.OK, "메뉴 목록 보기", respDtos), HttpStatus.OK);
    }

    @VerifyShop
    @PostMapping("/shop/menu")
    public ResponseEntity<?> saveMenu(@AuthenticationPrincipal PrincipalUser principalUser,
            @RequestBody MenuSaveReqDto menuSaveReqDto) {

        MenuSaveRespDto respDto = menuService.save(menuSaveReqDto, principalUser);

        return new ResponseEntity<>(new ResponseDto<>(HttpStatus.CREATED, "메뉴 저장", respDto), HttpStatus.CREATED);
    }

    @VerifyShop
    @DeleteMapping("/shop/menu/{id}")
    public ResponseEntity<?> deleteMenu(@AuthenticationPrincipal PrincipalUser principalUser, @PathVariable Long id) {

        menuService.delete(id, principalUser);
        return new ResponseEntity<>(new ResponseDto<>(HttpStatus.ACCEPTED, "메뉴 삭제", null), HttpStatus.ACCEPTED);
    }
}
