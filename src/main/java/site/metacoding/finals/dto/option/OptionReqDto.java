package site.metacoding.finals.dto.option;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import site.metacoding.finals.domain.option.Option;
import site.metacoding.finals.domain.option_shop.OptionShop;
import site.metacoding.finals.domain.shop.Shop;

public class OptionReqDto {

    @Setter
    @Getter
    public static class OptionSaveReqDto {
        Long optionList;

        public OptionShop toEntity(Option option, Shop shop) {
            return OptionShop.builder()
                    .shop(shop)
                    .option(option)
                    .build();
        }

    }
}
