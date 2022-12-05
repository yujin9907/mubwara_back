package site.metacoding.finals.domain.reservation;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("select r from Reservation r join fetch r.customer left join ShopTable st on r.shopTable = st left join Shop s on st.shop = s where s.id = :shopId")
    List<Reservation> findCustomerByShopId(@Param("shopId") Long shopId);

    List<Reservation> findByCustomerId(Long customerId);

    @Query(value = "select r.* from reservation r"
            + " right join (select * from shop_table where max_people= ?1) t on t.id = r.shop_table_id"
            + " where reservation_date= ?2", nativeQuery = true)
    List<Reservation> findByDataMaxPeople(int maxPeople, String reservaionTime);

}
