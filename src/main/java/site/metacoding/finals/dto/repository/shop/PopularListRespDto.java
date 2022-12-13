package site.metacoding.finals.dto.repository.shop;

public interface PopularListRespDto {
    Long getShopId();

    String getShopName();

    String getAddress();

    String getCategory();

    String getStoreFileName();

    String getOpenTime();

    String getCloseTime();

    String getPhoneNumber();

    Integer getCount();
}
