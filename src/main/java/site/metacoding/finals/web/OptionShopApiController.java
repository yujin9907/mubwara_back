package site.metacoding.finals.web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.metacoding.finals.config.auth.PrincipalUser;
import site.metacoding.finals.dto.ResponseDto;
import site.metacoding.finals.dto.option.OptionReqDto.OptionSaveReqDto;
import site.metacoding.finals.dto.option.OptionRespDto.OptionSaveRepsDto;
import site.metacoding.finals.service.OptionShopService;

@RestController
@RequiredArgsConstructor
public class OptionShopApiController {

    private final OptionShopService optionShopService;

    @PostMapping("/shop/option")
    public ResponseEntity<?> CustomerMypageReviewApi(@RequestBody List<OptionSaveReqDto> reqDto,
            @AuthenticationPrincipal PrincipalUser principalUser) {
        List<OptionSaveRepsDto> repsDtos = optionShopService.saveOption(reqDto, principalUser);
        return new ResponseEntity<>(new ResponseDto<>(HttpStatus.CREATED, "편의시설 등록", repsDtos), HttpStatus.CREATED);
    }
}
