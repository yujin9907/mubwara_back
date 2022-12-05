package site.metacoding.finals.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.finals.config.auth.PrincipalUser;
import site.metacoding.finals.domain.option.Option;
import site.metacoding.finals.domain.option_shop.OptionShopRepository;
import site.metacoding.finals.domain.shop.Shop;
import site.metacoding.finals.domain.shop.ShopRepository;
import site.metacoding.finals.dto.option.OptionReqDto.OptionSaveReqDto;

@RequiredArgsConstructor
@Service
public class OptionService {

}
