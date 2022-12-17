package site.metacoding.finals.domain.reservation;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import site.metacoding.finals.dto.repository.shop.AnalysisDto;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

        @Query("select r from Reservation r join fetch r.customer left join ShopTable st on r.shopTable = st left join Shop s on st.shop = s where s.id = :shopId")
        List<Reservation> findCustomerByShopId(@Param("shopId") Long shopId);

        @EntityGraph(attributePaths = "customer")
        @Query("select r from Reservation r where r.customer=customer")
        List<Reservation> findByCustomerId(Long customerId);

        @Query(value = "select r.* from reservation r"
                        + " right join (select * from shop_table where max_people= ?1) t on t.id = r.shop_table_id"
                        + " where reservation_date= ?2", nativeQuery = true)
        List<Reservation> findByDataMaxPeople(int maxPeople, String reservaionTime);

        @Query(value = "select r.t times, r.d dates, (r.a*r.b) results " +
                        "from " +
                        "(select r.reservation_time t, r.reservation_date d, sum(st.max_people) a, s.per_price b from reservation r "
                        +
                        "left join shop_table st on r.shop_table_id = st.id " +
                        "left join shop s on s.id = st.shop_id " +
                        "where s.id=:shopId and r.reservation_date=:date " +
                        "group by r.reservation_time) r", nativeQuery = true)
        List<AnalysisDto> findBySumDate(@Param("shopId") Long shopId, @Param("date") String date);

        @Query(value = "select re.date dates, (re.a*re.b) results " +
                        "from " +
                        "(select r.reservation_date date, sum(st.max_people) a, s.per_price b from " +
                        "reservation r " +
                        "left join shop_table st on r.shop_table_id = st.id " +
                        "left join shop s on s.id = st.shop_id " +
                        "where s.id=:shopId " +
                        "group by r.reservation_date) re", nativeQuery = true)
        List<AnalysisDto> findBySumWeek(@Param("shopId") Long shopId);
}
