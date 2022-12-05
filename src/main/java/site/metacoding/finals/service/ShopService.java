package site.metacoding.finals.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.metacoding.finals.config.auth.PrincipalUser;
import site.metacoding.finals.domain.image_file.ImageFile;
import site.metacoding.finals.domain.image_file.ImageFileRepository;
import site.metacoding.finals.domain.option.Option;
import site.metacoding.finals.domain.option.OptionRepository;
import site.metacoding.finals.domain.reservation.ReservationRepository;
import site.metacoding.finals.domain.shop.Shop;
import site.metacoding.finals.domain.shop.ShopRepository;
import site.metacoding.finals.domain.user.User;
import site.metacoding.finals.domain.user.UserRepository;
import site.metacoding.finals.dto.repository.shop.AnalysisDto;
import site.metacoding.finals.dto.reservation.ReservationReqDto.AnalysisDateReqDto;
import site.metacoding.finals.dto.shop.ShopReqDto.ShopInfoSaveReqDto;
import site.metacoding.finals.dto.shop.ShopReqDto.ShopJoinReqDto;
import site.metacoding.finals.dto.shop.ShopRespDto.ShopDetailRespDto;
import site.metacoding.finals.dto.shop.ShopRespDto.ShopInfoSaveRespDto;
import site.metacoding.finals.dto.shop.ShopRespDto.ShopJoinRespDto;
import site.metacoding.finals.dto.shop.ShopRespDto.ShopListRespDto;
import site.metacoding.finals.handler.ImageFileHandler;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShopService {

    @PersistenceContext
    private EntityManager em;

    private final UserRepository userRepository;
    private final ShopRepository shopRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ImageFileHandler imageFileHandler;
    private final ImageFileRepository imageFileRepository;
    private final ReservationRepository reservationRepository;

    @Transactional
    public ShopJoinRespDto join(ShopJoinReqDto shopJoinReqDto) {
        String encPassword = bCryptPasswordEncoder.encode(shopJoinReqDto.getPassword());
        shopJoinReqDto.setPassword(encPassword);

        User userPS = userRepository.save(shopJoinReqDto.toUserEntity());

        // userPS값을 바로 return하면 Entity에 영향이 가나?
        return new ShopJoinRespDto(userPS);
    }

    @Transactional
    public ShopInfoSaveRespDto information(ShopInfoSaveReqDto shopInfoSaveReqDto, PrincipalUser principalUser) {

        // shop information save
        Shop shopPS = shopRepository.save(shopInfoSaveReqDto.toInfoSaveEntity(principalUser.getUser()));

        // images save
        List<ImageFile> images = imageFileHandler.storeFile(shopInfoSaveReqDto.getImage(), null); // 여기 로직 수정
        images.forEach(image -> {
            imageFileRepository.save(image);
        });

        return new ShopInfoSaveRespDto(shopPS, images);
    }

    public List<AnalysisDto> analysisDate(PrincipalUser principalUser, AnalysisDateReqDto analysisDateReqDto) {

        // 가게 정보 조회
        Optional<Shop> shopPS = shopRepository.findByUserId(principalUser.getUser().getId());

        return reservationRepository.findBySumDate(shopPS.get().getId(),
                analysisDateReqDto.getDate());

    }

    public List<AnalysisDto> analysisWeek(PrincipalUser principalUser, AnalysisDateReqDto analysisDateReqDto) {

        // 가게 정보 조회
        Optional<Shop> shopPS = shopRepository.findByUserId(principalUser.getUser().getId());

        // 오늘 요일 구하기

        LocalDate date = analysisDateReqDto.toLocalDate();

        String todayWeek = date.getDayOfWeek().toString();

        return null;

    }

    public List<ShopListRespDto> List() {
        // em.clear();

        List<Shop> shopPS = shopRepository.findAllList();
        return shopPS.stream().map((shop) -> new ShopListRespDto(shop)).collect(Collectors.toList());
    }

    public List<ShopListRespDto> categoryList(String categoryName) {
        List<Shop> shopList = shopRepository.findByCategory(categoryName);

        return shopList.stream()
                .map((dto) -> new ShopListRespDto(dto)).collect(Collectors.toList());

    }

    public ShopDetailRespDto detatil(Long shopId) {
        // 가게 정보
        Shop shopPS = shopRepository.findById(shopId)
                .orElseThrow(() -> new RuntimeException("잘못된 가게 요청"));
        // 날짜 + 인원 => 예약 가능 시간 조회

        // // 가게 특징
        // Option featurePS = featureRepository.findByShopId(shopId)
        // .orElseThrow(() -> new RuntimeException("잘못된 가게 요청"));

        return null;
    }
}
