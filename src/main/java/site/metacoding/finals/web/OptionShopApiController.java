package site.metacoding.finals.web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.metacoding.finals.config.annotation.VerifyShop;
import site.metacoding.finals.config.auth.PrincipalUser;
import site.metacoding.finals.dto.ResponseDto;
import site.metacoding.finals.dto.option.OptionReqDto.OptionSaveReqDto;
import site.metacoding.finals.dto.option.OptionRespDto.OptionSaveRepsDto;
import site.metacoding.finals.service.OptionShopService;

@RestController
@RequiredArgsConstructor
public class OptionShopApiController {

    private final OptionShopService optionShopService;

    @VerifyShop
    @PostMapping("/shop/option")
    public ResponseEntity<?> SaveOption(@AuthenticationPrincipal PrincipalUser principalUser,
            @RequestBody OptionSaveReqDto reqDto) {
        List<OptionSaveRepsDto> repsDtos = optionShopService.saveOption(reqDto, principalUser);
        return new ResponseEntity<>(new ResponseDto<>(HttpStatus.CREATED, "편의시설 등록", repsDtos), HttpStatus.CREATED);
    }

    @VerifyShop
    @PutMapping("/shop/option")
    public ResponseEntity<?> updateOption(@AuthenticationPrincipal PrincipalUser principalUser,
            @RequestBody OptionSaveReqDto reqDto) {
        List<OptionSaveRepsDto> repsDtos = optionShopService.updateOption(reqDto, principalUser);
        return new ResponseEntity<>(new ResponseDto<>(HttpStatus.CREATED, "편의시설 수정", repsDtos), HttpStatus.CREATED);
    }
}
