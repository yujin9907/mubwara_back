package site.metacoding.finals.repositoryDto.customer;

public interface ReservationRepositoryRespDto {
    // shop.id shop_id, shop.shop_name, shop.address, shop.category,
    // r3.reservation_date, r3.reservation_time
    // Long getShopId();

    String getShopName();

    String getAddress();

    String getCategory();

    String getStoreFilename();

    String getReservationDate();

    String getReservationTime();

}
