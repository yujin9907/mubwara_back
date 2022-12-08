package site.metacoding.finals.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.finals.config.auth.PrincipalUser;
import site.metacoding.finals.domain.imagefile.ImageFile;
import site.metacoding.finals.domain.imagefile.ImageFileRepository;
import site.metacoding.finals.domain.menu.Menu;
import site.metacoding.finals.domain.menu.MenuRepository;
import site.metacoding.finals.domain.shop.Shop;
import site.metacoding.finals.domain.shop.ShopRepository;
import site.metacoding.finals.dto.image_file.ImageFileReqDto.ImageHandlerDto;
import site.metacoding.finals.dto.menu.MenuReqDto.MenuSaveReqDto;
import site.metacoding.finals.dto.menu.MenuRespDto.MenuSaveRespDto;
import site.metacoding.finals.handler.ImageFileHandler;

@RequiredArgsConstructor
@Service
public class MenuService {

    private final ShopRepository shopRepository;
    private final MenuRepository menuRepository;
    private final ImageFileRepository imageFileRepository;
    private final ImageFileHandler imageFileHandler;

    @Transactional
    public MenuSaveRespDto save(MenuSaveReqDto menuSaveReqDto, PrincipalUser principalUser) {
        Shop shopPS = shopRepository.findByUserId(principalUser.getUser().getId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 가게 입니다"));

        Menu menu = menuRepository.save(menuSaveReqDto.toEntity(shopPS));
        List<ImageHandlerDto> imageDto = imageFileHandler.storeFile(menuSaveReqDto.getImageFile());
        ImageFile imageFile = imageFileRepository.save(imageDto.get(0).toMenuEntity(menu));

        return new MenuSaveRespDto(menu, imageFile.getId());

    }
}
