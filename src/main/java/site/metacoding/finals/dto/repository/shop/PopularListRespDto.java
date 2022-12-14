package site.metacoding.finals.dto.repository.shop;

import site.metacoding.finals.domain.shop.Shop;

public interface PopularListRespDto {
    Long getShopId();

    String getShopName();

    String getAddress();

    String getCategory();

    String getStoreFileName();

    String getOpenTime();

    String getCloseTime();

    String getPhoneNumber();

    Double getScoreAvg();

    String getInformation();

    // default Shop toShopEntity(){
    // return Shop.builder()
    // .id(getShopId())
    // .shopName(getShopName())
    // .address(getAddress())
    // .category(getCategory())
    // .openTime(getOpenTime())
    // .closeTime(getCloseTime())
    // .phoneNumber(getPhoneNumber())
    // .imageFile(null)
    // }
}
