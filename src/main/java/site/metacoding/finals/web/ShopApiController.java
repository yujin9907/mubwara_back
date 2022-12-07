package site.metacoding.finals.web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.metacoding.finals.config.auth.PrincipalUser;
import site.metacoding.finals.dto.ResponseDto;
import site.metacoding.finals.dto.repository.shop.AnalysisDto;
import site.metacoding.finals.dto.reservation.ReservationReqDto.AnalysisDateReqDto;
import site.metacoding.finals.dto.reservation.ReservationRespDto.AnalysisWeekRespDto;
import site.metacoding.finals.dto.shop.ShopReqDto.ShopInfoSaveReqDto;
import site.metacoding.finals.dto.shop.ShopRespDto.ShopDetailRespDto;
import site.metacoding.finals.dto.shop.ShopRespDto.ShopInfoSaveRespDto;
import site.metacoding.finals.dto.shop.ShopRespDto.ShopListRespDto;
import site.metacoding.finals.service.ShopService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ShopApiController {

    private final ShopService shopService;

    // shop입장에서 이용하는 가게 기능

    // shop 한 개 만 만들도록 제한 / respDto LAZY 로딩 안되도록 좀 더 정확히 만들어줘야 함
    @PostMapping(value = "/shop/save")
    public ResponseEntity<?> saveShop(@RequestBody ShopInfoSaveReqDto shopInfoSaveReqDto,
            @AuthenticationPrincipal PrincipalUser principalUser) {
        ShopInfoSaveRespDto shopInfoSaveRespDto = shopService.save(shopInfoSaveReqDto, principalUser);
        return new ResponseEntity<>(new ResponseDto<>(HttpStatus.CREATED, "가게 정보등록 완료", shopInfoSaveRespDto),
                HttpStatus.CREATED);
    }

    @PostMapping("/shop/analysis/date")
    public ResponseEntity<?> dateAnalysis(@AuthenticationPrincipal PrincipalUser principalUser,
            @RequestBody AnalysisDateReqDto reqDto) {
        List<AnalysisDto> respDto = shopService.analysisDate(principalUser, reqDto);
        return new ResponseEntity<>(new ResponseDto<>(HttpStatus.OK, "일일 통계", respDto),
                HttpStatus.OK);
    }

    @PostMapping("/shop/analysis/week")
    public ResponseEntity<?> weekAnalysis(@AuthenticationPrincipal PrincipalUser principalUser,
            @RequestBody AnalysisDateReqDto reqDto) {
        List<AnalysisWeekRespDto> respDtos = shopService.analysisWeek(principalUser, reqDto);
        return new ResponseEntity<>(new ResponseDto<>(HttpStatus.OK, "주간 통계", respDtos),
                HttpStatus.OK);
    }

    // customer입장에서 보는 가게 기능
    // 네임, 주소, 전화번호, 오픈클로즈, 사진
    // 리뷰 들고 오는 로직 진심 수정해야 됨
    @GetMapping("/list")
    public ResponseEntity<?> listShop() {
        List<ShopListRespDto> shopList = shopService.List();
        return new ResponseEntity<>(new ResponseDto<>(HttpStatus.OK, "전체 가게 리스트 조회", shopList), HttpStatus.OK);
    }

    // 리스폰스 dto 방식 얘만 다름
    @GetMapping("/list/{categoryName}")
    public ResponseEntity<?> shopCategoryList(@PathVariable String categoryName) {
        List<ShopListRespDto> shopList = shopService.categoryList(categoryName);
        return new ResponseEntity<>(new ResponseDto<>(HttpStatus.OK, "카테고리별 가게 리스트 조회", shopList), HttpStatus.OK);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<?> shopDetail(@PathVariable Long id) {
        ShopDetailRespDto dto = shopService.detatil(id);
        return new ResponseEntity<>(new ResponseDto<>(HttpStatus.OK, "가게 상세보기 조회", dto), HttpStatus.OK);
    }
}
