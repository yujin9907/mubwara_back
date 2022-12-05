package site.metacoding.finals.dto.shop_table;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import site.metacoding.finals.domain.shop.Shop;
import site.metacoding.finals.domain.shop_table.ShopTable;

public class ShopTableReqDto {

    @Setter
    @Getter
    public static class ShopTableUpdateReqDto {
        private List<ShopTableQtyDto> shopTableQtyDtoList;

        @Setter
        @Getter
        public static class ShopTableQtyDto {
            private int maxPeople;
            private int qty;
        }

        public ShopTable toShopTableEntity(int maxPeople, Shop shop) {
            return ShopTable.builder()
                    .maxPeople(maxPeople)
                    .shop(shop)
                    .isActive(true)
                    .build();
        }
    }
}
