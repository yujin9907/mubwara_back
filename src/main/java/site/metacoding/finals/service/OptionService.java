package site.metacoding.finals.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.finals.config.auth.PrincipalUser;
import site.metacoding.finals.domain.option.Option;
import site.metacoding.finals.domain.option.OptionRepository;
import site.metacoding.finals.dto.option.OptionRespDto.OptionListRespDto;

@RequiredArgsConstructor
@Service
public class OptionService {

    private final OptionRepository optionRepository;

    @Transactional(readOnly = true)
    public List<OptionListRespDto> optionList(PrincipalUser principalUser) {

        List<Option> optionPS = optionRepository.findByShopId(principalUser.getShop().getId());
        return optionPS.stream().map((o) -> new OptionListRespDto(o)).collect(Collectors.toList());
    }
}
