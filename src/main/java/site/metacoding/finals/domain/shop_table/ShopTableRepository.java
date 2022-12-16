package site.metacoding.finals.domain.shop_table;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import site.metacoding.finals.dto.repository.shop.QtyTableDto;

public interface ShopTableRepository extends JpaRepository<ShopTable, Long> {

        List<ShopTable> findByShopId(Long shopId);

        @Query(value = "select * from shop_table where shop_id= ?1 and max_people= ?2", nativeQuery = true)
        public List<ShopTable> findByMaxPeople(Long id, int maxPeople);

        @Query(value = "select * from shop_table where shop_id= ?1 and max_people= ?2 AND is_active = 1"
                        + " order by id asc limit 1", nativeQuery = true)
        ShopTable findByMaxPeopleToMinId(Long shopId, int maxPeople);

        @Query(value = "select distinct(max_people) from shop_table where shop_id= ?1 order by max_people", nativeQuery = true)
        Optional<List<Integer>> findDistinctByShopId(Long id);

        @Query(value = "select r3.* from " +
                        "(select s.*, r2.reservation_date from shop_table s " +
                        "left outer join (select * from reservation r where r.reservation_date=:date and r.reservation_time=:time) r2 "
                        +
                        "on s.id = r2.shop_table_id " +
                        "where s.shop_id=:shopId and s.max_people=:people) r3 where r3.reservation_date is null " +
                        "limit 1 ", nativeQuery = true)
        ShopTable findByDataAndTimeAndPeople(@Param("shopId") Long shopId, @Param("date") String date,
                        @Param("time") String time, @Param("people") int people);

        // -----------------------------------------------------------------------------------------

        @Query(value = "select max_people as maxPeople, count(max_people) qty from shop_table group by max_people", nativeQuery = true)
        List<QtyTableDto> findQtyTable();

        @Query(value = "select max_people as maxPeople, count(max_people) qty from shop_table group by max_people having max_people=:people", nativeQuery = true)
        QtyTableDto findQtyTableByNum(@Param("people") int people);

}