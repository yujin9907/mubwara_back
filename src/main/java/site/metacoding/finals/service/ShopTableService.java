package site.metacoding.finals.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.metacoding.finals.domain.shop.Shop;
import site.metacoding.finals.domain.shop.ShopRepository;
import site.metacoding.finals.domain.shop_table.ShopTable;
import site.metacoding.finals.domain.shop_table.ShopTableRepository;
import site.metacoding.finals.dto.shop_table.ShopTableReqDto.ShopTableUpdateReqDto;
import site.metacoding.finals.dto.shop_table.ShopTableReqDto.ShopTableUpdateReqDto.ShopTableQtyDto;
import site.metacoding.finals.dto.shop_table.ShopTableRespDto.AllShopTableRespDto;
import site.metacoding.finals.dto.shop_table.ShopTableRespDto.ShopTableSaveRespDto;

@Slf4j
@RequiredArgsConstructor
@Service
public class ShopTableService {

    private final ShopTableRepository shopTableRepository;
    private final ShopRepository shopRepository;

    @Transactional
    public AllShopTableRespDto findAllByShopId(Long userId) {
        Shop shopPS = shopRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("해당 가게가 없습니다."));

        return new AllShopTableRespDto(shopTableRepository.findByShopId(shopPS.getId()));
    }

    @Transactional
    public ShopTableSaveRespDto update(ShopTableUpdateReqDto shopTableUpdateReqDto, Long userId) {
        // 가게 확인
        Shop shopPS = shopRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("해당 가게가 없습니다."));
        log.debug("디버그 : 가게ID " + shopPS.getId());

        for (ShopTableQtyDto shopTableDto : shopTableUpdateReqDto.getShopTableQtyDtoList()) {
            if (shopTableDto.getQty() > 0) {
                for (int i = 0; i < shopTableDto.getQty(); i++) {
                    shopTableRepository
                            .save(shopTableUpdateReqDto.toShopTableEntity(shopTableDto.getMaxPeople(), shopPS));
                    log.debug("디버그 : shopTableUpdateReqDto 값" + shopTableUpdateReqDto
                            .toShopTableEntity(shopTableDto.getMaxPeople(), shopPS).getMaxPeople());
                }
            }
            log.debug("디버그 : save로직 실행 완료");
            if (shopTableDto.getQty() < 0) {
                for (int i = 0; i < Math.abs(shopTableDto.getQty()); i++) {
                    ShopTable shopTable = shopTableRepository.findByMaxPeopleToMinId(shopPS.getId(),
                            shopTableDto.getMaxPeople());
                    shopTable.deleteShopTable(shopTable.getId());
                }
            }
            log.debug("디버그 : delete로직 실행 완료");
        }

        return new ShopTableSaveRespDto(shopTableRepository.findByShopId(shopPS.getId()));
    }
}
