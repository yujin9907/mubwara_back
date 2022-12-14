package site.metacoding.finals.dto.shop;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import site.metacoding.finals.config.enums.Role;
import site.metacoding.finals.domain.option.Option;
import site.metacoding.finals.domain.shop.Shop;
import site.metacoding.finals.domain.user.User;

public class ShopReqDto {

    @Setter
    @Getter
    public static class ShopUpdateReqDto {
        private String shopName;
        private String phoneNumber;
        private String address;
        private String category;
        private String information;
        private String openTime;
        private String closeTime;
        private int perPrice;
        private int perHour;
        private List<String> image;

        public Shop toEntity(User user) {
            return Shop.builder()
                    .shopName(shopName)
                    .phoneNumber(phoneNumber)
                    .category(category)
                    .address(address)
                    .information(information)
                    .openTime(openTime)
                    .closeTime(closeTime)
                    .perPrice(perPrice)
                    .perHour(perHour)
                    .user(user)
                    .build();
        }
    }

    @Setter
    @Getter
    public static class ShopSaveReqDto {
        private String shopName;
        private String phoneNumber;
        private String address;
        private String category;
        private String information;
        private String openTime;
        private String closeTime;
        private int perPrice;
        private int perHour;
        private List<String> image;

        public Shop toEntity(User user) {
            return Shop.builder()
                    .shopName(shopName)
                    .phoneNumber(phoneNumber)
                    .category(category)
                    .address(address)
                    .information(information)
                    .openTime(openTime)
                    .closeTime(closeTime)
                    .perPrice(perPrice)
                    .perHour(perHour)
                    .user(user)
                    .build();
        }
    }

    @Getter
    @Setter
    public static class ShopFilterReqDto {
        private int date;
        private int person;
        private int time;
    }

    @Setter
    @Getter
    public static class OptionListReqDto {
        private Long option;
    }

}
