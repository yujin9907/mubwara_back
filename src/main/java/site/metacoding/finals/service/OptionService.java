package site.metacoding.finals.service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.finals.config.auth.PrincipalUser;
import site.metacoding.finals.config.exception.RuntimeApiException;
import site.metacoding.finals.domain.option.Option;
import site.metacoding.finals.domain.option.OptionRepository;
import site.metacoding.finals.domain.shop.Shop;
import site.metacoding.finals.domain.shop.ShopRepository;
import site.metacoding.finals.dto.option.OptionRespDto.OptionListRespDto;

@RequiredArgsConstructor
@Service
public class OptionService {

    private final OptionRepository optionRepository;
    private final ShopRepository shopRepository;

    @Transactional(readOnly = true)
    public List<OptionListRespDto> optionList(PrincipalUser principalUser) {
        Shop shopPS = shopRepository.findByUserId(principalUser.getId())
                .orElseThrow(() -> new RuntimeApiException("잘못된 가게 요청입니다", HttpStatus.NOT_FOUND));

        List<Option> optionPS = optionRepository.findByShopId(shopPS.getId());
        return optionPS.stream().map((o) -> new OptionListRespDto(o)).collect(Collectors.toList());
    }
}
