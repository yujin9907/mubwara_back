package site.metacoding.finals.service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.finals.config.annotation.VerifyShop;
import site.metacoding.finals.config.auth.PrincipalUser;
import site.metacoding.finals.config.exception.RuntimeApiException;
import site.metacoding.finals.domain.imagefile.ImageFile;
import site.metacoding.finals.domain.imagefile.ImageFileRepository;
import site.metacoding.finals.domain.menu.Menu;
import site.metacoding.finals.domain.menu.MenuRepository;
import site.metacoding.finals.domain.shop.Shop;
import site.metacoding.finals.domain.shop.ShopRepository;
import site.metacoding.finals.dto.image_file.ImageFileReqDto.ImageHandlerDto;
import site.metacoding.finals.dto.menu.MenuReqDto.MenuSaveReqDto;
import site.metacoding.finals.dto.menu.MenuRespDto.MenuListRespDto;
import site.metacoding.finals.dto.menu.MenuRespDto.MenuSaveRespDto;
import site.metacoding.finals.handler.ImageFileHandler;

@RequiredArgsConstructor
@Service
public class MenuService {

    private final ShopRepository shopRepository;
    private final MenuRepository menuRepository;
    private final ImageFileRepository imageFileRepository;
    private final ImageFileHandler imageFileHandler;

    @Transactional(readOnly = true)
    public List<MenuListRespDto> list(PrincipalUser principalUser) {
        // 검증 =aop

        List<Menu> menus = menuRepository.findByShopId(principalUser.getShop().getId());
        return menus.stream().map((m) -> new MenuListRespDto(m)).collect(Collectors.toList());

    }

    @Transactional
    public MenuSaveRespDto save(MenuSaveReqDto menuSaveReqDto, PrincipalUser principalUser) {

        Menu menu = menuRepository.save(menuSaveReqDto.toEntity(principalUser.getShop()));
        List<ImageHandlerDto> imageDto = imageFileHandler.storeFile(menuSaveReqDto.getImageFile());
        ImageFile imageFile = imageFileRepository.save(imageDto.get(0).toMenuEntity(menu));

        return new MenuSaveRespDto(menu, imageFile.getId());

    }

    @Transactional
    public void delete(Long id, PrincipalUser principalUser) {

        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeApiException("존재하지 않는 메뉴입니다.", HttpStatus.NOT_FOUND));

        menuRepository.delete(menu);
    }
}
