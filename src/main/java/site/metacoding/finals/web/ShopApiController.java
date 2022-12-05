package site.metacoding.finals.web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.metacoding.finals.dto.ResponseDto;
import site.metacoding.finals.dto.shop.ShopReqDto.ShopJoinReqDto;
import site.metacoding.finals.dto.shop.ShopRespDto.ShopDetailRespDto;
import site.metacoding.finals.dto.shop.ShopRespDto.ShopJoinRespDto;
import site.metacoding.finals.dto.shop.ShopRespDto.ShopListRespDto;
import site.metacoding.finals.service.ShopService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ShopApiController {

    private final ShopService shopService;

    // shop입장에서 이용하는 가게 기능

    @PostMapping("/shop/join")
    public ResponseEntity<?> joinShopApi(@RequestBody ShopJoinReqDto shopJoinReqDto) {
        ShopJoinRespDto shopJoinRespDto = shopService.join(shopJoinReqDto);
        return new ResponseEntity<>(new ResponseDto<>(HttpStatus.CREATED, "가게 회원가입 완료", shopJoinRespDto),
                HttpStatus.CREATED);
    }

    // shop 한 개 만 만들도록 제한 / respDto LAZY 로딩 안되도록 좀 더 정확히 만들어줘야 함
    // @PostMapping(value = "/shop/information", consumes = {
    // MediaType.APPLICATION_JSON_VALUE,
    // MediaType.MULTIPART_FORM_DATA_VALUE })
    // public ResponseEntity<?> save(@RequestPart("file") List<MultipartFile> file,
    // @RequestPart("reqDto") ShopInfoSaveReqDto shopInfoSaveReqDto,
    // @AuthenticationPrincipal PrincipalUser principalUser) {
    // log.debug("디버그 : principalUser.getId " + principalUser.getUser().getId());
    // ShopInfoSaveRespDto shopInfoSaveRespDto = shopService.information(file,
    // shopInfoSaveReqDto,
    // principalUser.getUser());
    // return new ResponseEntity<>(new ResponseDto<>(HttpStatus.CREATED, "가게 정보등록
    // 완료", shopInfoSaveRespDto),
    // HttpStatus.CREATED);
    // }

    // customer입장에서 보는 가게 기능
    // 네임, 주소, 전화번호, 오픈클로즈, 사진
    @GetMapping("/shop/list")
    public ResponseEntity<?> shopList() {
        List<ShopListRespDto> shopList = shopService.List();
        return new ResponseEntity<>(new ResponseDto<>(HttpStatus.OK, "전체 가게 리스트 조회", shopList), HttpStatus.OK);
    }

    // 리스폰스 dto 방식 얘만 다름
    @GetMapping("/shop/list/{categoryName}")
    public ResponseEntity<?> shopCategoryList(@PathVariable String categoryName) {
        List<ShopListRespDto> shopList = shopService.categoryList(categoryName);
        return new ResponseEntity<>(new ResponseDto<>(HttpStatus.OK, "카테고리별 가게 리스트 조회", shopList), HttpStatus.OK);
    }

    @GetMapping("/shop/detail/{id}")
    public ResponseEntity<?> shopDetail(@PathVariable Long id) {
        ShopDetailRespDto dto = shopService.detatil(id);
        return new ResponseEntity<>(new ResponseDto<>(HttpStatus.OK, "가게 상세보기 조회", dto), HttpStatus.OK);
    }
}
