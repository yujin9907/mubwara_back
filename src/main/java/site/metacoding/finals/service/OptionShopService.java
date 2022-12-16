package site.metacoding.finals.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.finals.config.auth.PrincipalUser;
import site.metacoding.finals.domain.option.Option;
import site.metacoding.finals.domain.option.OptionRepository;
import site.metacoding.finals.domain.option_shop.OptionShop;
import site.metacoding.finals.domain.option_shop.OptionShopRepository;
import site.metacoding.finals.domain.shop.Shop;
import site.metacoding.finals.domain.shop.ShopRepository;
import site.metacoding.finals.dto.option.OptionReqDto.OptionSaveReqDto;
import site.metacoding.finals.dto.option.OptionReqDto.OptionShopSaveDto;
import site.metacoding.finals.dto.option.OptionRespDto.OptionSaveRepsDto;

@Service
@RequiredArgsConstructor
public class OptionShopService {

    private final OptionShopRepository optionShopRepository;
    private final ShopRepository shopRepository;
    private final OptionRepository optionRepository;

    @Transactional
    public List<OptionSaveRepsDto> saveOption(OptionSaveReqDto reqDtos, PrincipalUser principalUser) {
        // 옵션 찾기 및 optionshop에 저장
        List<OptionShop> result = new ArrayList<>();
        reqDtos.getOptionList().forEach((opiton) -> {
            Option option = optionRepository.findById(opiton).get();
            OptionShop optionShop = optionShopRepository
                    .save(new OptionShopSaveDto(principalUser.getShop(), option).toEntity());
            result.add(optionShop);
        });

        return result.stream().map((r) -> new OptionSaveRepsDto(r)).collect(Collectors.toList());
    }

    @Transactional
    public List<OptionSaveRepsDto> updateOption(OptionSaveReqDto reqDtos, PrincipalUser principalUser) {
        // 옵션 전체 삭제
        // System.out.println("디버그 : " + principalUser.getShop().getId());
        optionShopRepository.deleteByShopId(principalUser.getShop().getId());

        // 옵션 세이브
        return saveOption(reqDtos, principalUser);
    }
}
