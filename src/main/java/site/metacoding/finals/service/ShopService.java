package site.metacoding.finals.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.metacoding.finals.config.auth.PrincipalUser;
import site.metacoding.finals.config.exception.RuntimeApiException;
import site.metacoding.finals.domain.imagefile.ImageFileRepository;
import site.metacoding.finals.domain.reservation.ReservationRepository;
import site.metacoding.finals.domain.review.ReviewRepository;
import site.metacoding.finals.domain.shop.Shop;
import site.metacoding.finals.domain.shop.ShopQueryRepository;
import site.metacoding.finals.domain.shop.ShopRepository;
import site.metacoding.finals.domain.user.User;
import site.metacoding.finals.domain.user.UserRepository;
import site.metacoding.finals.dto.image_file.ImageFileReqDto.ImageHandlerDto;
import site.metacoding.finals.dto.repository.shop.AnalysisDto;
import site.metacoding.finals.dto.repository.shop.PopularListRespDto;
import site.metacoding.finals.dto.reservation.ReservationReqDto.AnalysisDateReqDto;
import site.metacoding.finals.dto.reservation.ReservationRespDto.AnalysisWeekRespDto;
import site.metacoding.finals.dto.shop.ShopReqDto.OptionListReqDto;
import site.metacoding.finals.dto.shop.ShopReqDto.ShopSaveReqDto;
import site.metacoding.finals.dto.shop.ShopReqDto.ShopUpdateReqDto;
import site.metacoding.finals.dto.shop.ShopRespDto.OptionListRespDto;
import site.metacoding.finals.dto.shop.ShopRespDto.PriceListRespDto;
import site.metacoding.finals.dto.shop.ShopRespDto.ShopDetailRespDto;
import site.metacoding.finals.dto.shop.ShopRespDto.ShopInfoSaveRespDto;
import site.metacoding.finals.dto.shop.ShopRespDto.ShopListRespDto;
import site.metacoding.finals.dto.shop.ShopRespDto.ShopPopularListRespDto;
import site.metacoding.finals.dto.shop.ShopRespDto.ShopSearchListRespDto;
import site.metacoding.finals.dto.shop.ShopRespDto.ShopUpdateRespDto;
import site.metacoding.finals.handler.ImageFileHandler;

@Slf4j
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;
    private final ImageFileHandler imageFileHandler;
    private final ImageFileRepository imageFileRepository;
    private final ReservationRepository reservationRepository;
    private final ReviewRepository reviewrRepository;
    private final ShopQueryRepository shopQueryRepository;
    private final UserRepository userRepository;

    @Transactional
    public ShopInfoSaveRespDto save(ShopSaveReqDto shopSaveReqDto, PrincipalUser principalUser) {
        // 검증
        User user = userRepository.findById(principalUser.getId())
                .orElseThrow(() -> new RuntimeApiException("잘못된 유저입니다", HttpStatus.NOT_FOUND));

        // GrantedAuthority update shop
        user.updateToShop();
        userRepository.save(user);

        // shop information save
        Shop shopPS = shopRepository.save(shopSaveReqDto.toEntity(user));

        // images save
        List<ImageHandlerDto> images = imageFileHandler.storeFile(shopSaveReqDto.getImage());
        images.forEach(image -> {
            imageFileRepository.save(image.toShopEntity(shopPS));
        });

        return new ShopInfoSaveRespDto(shopPS);
    }

    @Transactional
    public ShopUpdateRespDto update(ShopUpdateReqDto reqDto, PrincipalUser principalUser) {
        // 검증

        // shop information save
        Shop shopPS = shopRepository.save(reqDto.toEntity(principalUser.getUser()));

        // images save
        List<ImageHandlerDto> images = imageFileHandler.storeFile(reqDto.getImage());
        images.forEach(image -> {
            imageFileRepository.save(image.toShopEntity(shopPS));
        });

        return new ShopUpdateRespDto(shopPS, images.get(0).getStoreFilename());
    }

    public ShopUpdateRespDto updatePage(PrincipalUser principalUser) {

        return new ShopUpdateRespDto(principalUser.getShop());
    }

    public List<AnalysisDto> analysisDate(PrincipalUser principalUser, AnalysisDateReqDto analysisDateReqDto) {

        return reservationRepository.findBySumDate(principalUser.getShop().getId(),
                analysisDateReqDto.getDate());

    }

    public List<AnalysisWeekRespDto> analysisWeek(PrincipalUser principalUser, AnalysisDateReqDto analysisDateReqDto) {

        List<AnalysisDto> weekDtos = reservationRepository.findBySumWeek(principalUser.getShop().getId());

        return weekDtos.stream().map((w) -> new AnalysisWeekRespDto(w)).collect(Collectors.toList());

    }

    public List<ShopListRespDto> List() {
        List<Shop> shopPS = shopRepository.findAllList();

        List<ShopListRespDto> respDtos = shopPS.stream().map((shop) -> new ShopListRespDto(shop))
                .collect(Collectors.toList());

        respDtos.forEach(dto -> {
            if (reviewrRepository.findByShopId(dto.getId()).size() != 0) {
                dto.setScoreAvg(reviewrRepository.findByAvgScore(dto.getId()).getScore());
            } else {
                dto.setScoreAvg(0.0);
            }
        });
        return respDtos;
    }

    public ShopDetailRespDto detatil(Long shopId) {
        // 가게 정보
        Shop shopPS = shopRepository.findById(shopId)
                .orElseThrow(() -> new RuntimeException("잘못된 가게 요청"));
        // 날짜 + 인원 => 예약 가능 시간 조회

        // 리뷰 관련
        ShopDetailRespDto respDto = new ShopDetailRespDto(shopPS);

        if (reviewrRepository.findByShopId(shopId).size() != 0) {
            respDto.setScoreAvg(reviewrRepository.findByAvgScore(shopId).getScore());
        }

        return respDto;
    }

    public List<ShopListRespDto> categoryList(String categoryName) {
        List<Shop> shopList = shopRepository.findByCategory(categoryName);

        return shopList.stream()
                .map((dto) -> new ShopListRespDto(dto)).collect(Collectors.toList());

    }

    public List<ShopPopularListRespDto> popularList() {
        List<PopularListRespDto> dtos = shopRepository.findByPopularList();

        // System.out.println("디버그 : " + dtos.size());

        return dtos.stream().map((d) -> new ShopPopularListRespDto(d)).collect(Collectors.toList());

    }

    public Map<String, Object> optionList(List<OptionListReqDto> reqDto) {
        List<OptionListRespDto> dtoPS = shopQueryRepository.findOptionListByOptionId(reqDto);
        Map<String, Object> dto = new HashMap<>();
        dto.put("option", reqDto);
        dto.put("shop", dtoPS);
        return dto;
    }

    public List<PriceListRespDto> priceList(String value) {

        return shopQueryRepository.findByPriceList(value);

    }

    public List<ShopSearchListRespDto> searchList(String keyword) {

        System.out.println("디버그 : " + keyword);

        List<Shop> shopPS = shopRepository.findBySearchList(keyword)
                .orElseThrow(() -> new RuntimeApiException(keyword + "해당 검색 결과 없음", HttpStatus.NOT_FOUND));

        System.out.println("디버그 : " + shopPS.size());

        return shopPS.stream().map((s) -> new ShopSearchListRespDto(s)).collect(Collectors.toList());
    }

}
