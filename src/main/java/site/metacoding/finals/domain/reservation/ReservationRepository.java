package site.metacoding.finals.domain.reservation;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByCustomerId(Long customerId);

    @Query(value = "select r.* from reservation r"
            + " right join (select * from shop_table where max_people= ?1) t on t.id = r.shop_table_id"
            + " where reservation_date= ?2", nativeQuery = true)
    List<Reservation> findByDataMaxPeople(int maxPeople, String reservaionTime);

}
