package site.metacoding.finals.domain.shop;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import site.metacoding.finals.repositoryDto.customer.ReservationRepositoryRespDto;

public interface ShopRepository extends JpaRepository<Shop, Long> {


        @Query("select s from Shop s")
        List<Shop> findAllList();

        @Query("select s from Shop s join fetch s.imageFile where s.id = ?1")
        Shop findByShopId(@Param("id") Long id);

        Optional<Shop> findByUserId(Long userId);


        @Query("select s from Shop s where s.category = :category")
        List<Shop> findByCategory(@Param("category") String category);

        @Query(value = "select i.store_filename as storeFilename, r4.shop_name as shopName, r4.category as category, " +
                        "r4.reservation_time as reservationTime, r4.reservation_date as reservationDate " +
                        "from imagefile i " +
                        "right join (select shop.id, shop.shop_name, shop.address, shop.category, r3.reservation_date, r3.reservation_time from shop "
                        +
                        "right join (select st.shop_id, r2.* from shop_table st " +
                        "right join (select r.* from reservation r where customer_id= ?1) r2 " +
                        "on st.id = r2.shop_table_id) r3 " +
                        "on shop.id=r3.shop_id) r4 " +
                        "on r4.id=i.shop_id", nativeQuery = true)
        List<ReservationRepositoryRespDto> findResevationByCustomerId(@Param("customerId") Long customerId);

        @Query(value = "select shop.id shopId, shop.shop_name shopName, shop.address address, shop.category category, r3.reservation_date reservationDate, r3.reservation_time reservationTime from shop "
                        +
                        "right join (select st.shop_id, r2.* from shop_table st " +
                        "right join (select r.* from reservation r where customer_id= ?1) r2 " +
                        "on st.id = r2.shop_table_id) r3 " +
                        "on shop.id=r3.shop_id", nativeQuery = true)
        List<ReservationRepositoryRespDto> findResevationByCustomerIdTEST(@Param("customerId") Long customerId);

        @Query("select s from Shop s  join fetch s.imageFile " +
                        "right join Subscribe sb on sb.shop = s " +
                        "right join Customer c on c = sb.customer " +
                        "where c.id=?1")
        List<Shop> findSubscribeByCustomerId(Long id);

}
