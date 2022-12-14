package site.metacoding.finals.web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.metacoding.finals.config.annotation.VerifyShop;
import site.metacoding.finals.config.auth.PrincipalUser;
import site.metacoding.finals.dto.ResponseDto;
import site.metacoding.finals.dto.option.OptionRespDto.OptionListRespDto;
import site.metacoding.finals.service.OptionService;

@RestController
@RequiredArgsConstructor
public class OptionApiController {

    private final OptionService optionService;

    @VerifyShop
    @GetMapping("/shop/option")
    public ResponseEntity<?> optionList(@AuthenticationPrincipal PrincipalUser principalUser) {
        List<OptionListRespDto> respDto = optionService.optionList(principalUser);
        return new ResponseEntity<>(new ResponseDto<>(HttpStatus.OK, "보유 편의시설 목록보기", respDto), HttpStatus.OK);
    }

}
