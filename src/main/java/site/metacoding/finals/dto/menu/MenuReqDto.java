package site.metacoding.finals.dto.menu;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import site.metacoding.finals.domain.menu.Menu;
import site.metacoding.finals.domain.shop.Shop;

public class MenuReqDto {

    @Setter
    @Getter
    public static class MenuSaveReqDto {
        private String name;
        private Integer price;
        private Integer recommanded;
        private Long shopId;
        private List<String> imageFile;

        public Menu toEntity(Shop shop) {
            return Menu.builder()
                    .name(name)
                    .price(price)
                    .recommanded(recommanded)
                    .shop(shop)
                    .isDeleted(false)
                    .build();
        }

    }
}
