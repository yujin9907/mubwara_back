package site.metacoding.finals.web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.metacoding.finals.config.exception.RuntimeApiException;
import site.metacoding.finals.dto.ResponseDto;
import site.metacoding.finals.dto.commercial.CommercialRespDto.CommercialListRespDto;
import site.metacoding.finals.service.CommercialService;

@RestController
@RequiredArgsConstructor
public class CommercialApiController {

    private final CommercialService commercialService;

    @GetMapping("/commercial")
    public ResponseEntity<?> viewCommercial() {
        // throw new RuntimeApiException("예외 발생", HttpStatus.BAD_GATEWAY);
        List<CommercialListRespDto> respDtos = commercialService.listCommercial();
        return new ResponseEntity<>(new ResponseDto<>(HttpStatus.OK, "광고 리스트",
                respDtos), HttpStatus.OK);
    }
}
