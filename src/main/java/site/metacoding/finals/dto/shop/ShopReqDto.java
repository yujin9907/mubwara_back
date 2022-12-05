package site.metacoding.finals.dto.shop;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import site.metacoding.finals.config.enums.Role;
import site.metacoding.finals.domain.feature.Feature;
import site.metacoding.finals.domain.shop.Shop;
import site.metacoding.finals.domain.user.User;

public class ShopReqDto {

    // 회지DTO
    @Setter
    @Getter
    public static class ShopJoinReqDto {
        private String username;
        private String password;

        public User toUserEntity() {
            return User.builder()
                    .username(this.username)
                    .password(this.password)
                    .role(Role.SHOP)
                    .build();
        }
    }

    @Setter
    @Getter
    public static class ShopInfoSaveReqDto {
        private String shopName;
        private String phoneNumber;
        private String address;
        private String category;
        private String information;
        private String openTime;
        private String closeTime;
        private int perPrice;
        private int perHour;
        private List<String> featureNameList;

        public Shop toInfoSaveEntity(User user) {
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

        public Feature toFeatureEntity(String featureName, Shop shop) {
            return Feature.builder()
                    .name(featureName)
                    .shop(shop)
                    .build();
        }
    }

    // 유진 Dto
    @Getter
    @Setter
    public static class ShopFilterReqDto {
        private int date;
        private int person;
        private int time;
    }

}
