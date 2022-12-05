package site.metacoding.finals.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.metacoding.finals.domain.feature.Feature;
import site.metacoding.finals.domain.feature.FeatureRepository;
import site.metacoding.finals.domain.image_file.ImageFile;
import site.metacoding.finals.domain.image_file.ImageFileRepository;
import site.metacoding.finals.domain.shop.Shop;
import site.metacoding.finals.domain.shop.ShopRepository;
import site.metacoding.finals.domain.user.User;
import site.metacoding.finals.domain.user.UserRepository;
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
    private final FeatureRepository featureRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ImageFileRepository imageFileRepository;
    private final ImageFileHandler imageFileHandler;

    @Transactional
    public ShopJoinRespDto join(ShopJoinReqDto shopJoinReqDto) {
        String encPassword = bCryptPasswordEncoder.encode(shopJoinReqDto.getPassword());
        shopJoinReqDto.setPassword(encPassword);

        User userPS = userRepository.save(shopJoinReqDto.toUserEntity());

        // userPS값을 바로 return하면 Entity에 영향이 가나?
        return new ShopJoinRespDto(userPS);
    }

    // @Transactional
    // public ShopInfoSaveRespDto information(List<MultipartFile> multipartFiles,
    // ShopInfoSaveReqDto shopInfoSaveReqDto, User user) {

    // // shop information save
    // Shop shopPS = shopRepository.save(shopInfoSaveReqDto.toInfoSaveEntity(user));

    // // feature save
    // List<Feature> featureList = new ArrayList<>();
    // for (String name : shopInfoSaveReqDto.getFeatureNameList()) {
    // Feature feature =
    // featureRepository.save(shopInfoSaveReqDto.toFeatureEntity(name, shopPS));
    // featureList.add(feature);
    // }

    // // images save
    // List<ImageFile> images = imageFileHandler.storeFile(multipartFiles);
    // for (ImageFile img : images) {
    // img.setShop(shopPS);
    // imageFileRepository.save(img);
    // }

    // return new ShopInfoSaveRespDto(shopPS, featureList, images);
    // }

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

        // 가게 특징
        Feature featurePS = featureRepository.findByShopId(shopId)
                .orElseThrow(() -> new RuntimeException("잘못된 가게 요청"));

        return new ShopDetailRespDto(shopPS, featurePS);
    }
}
