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

    @Query(value = "select * from shop_table " +
            "where id != (select st.id from  shop_table st " +
            "inner join reservation r on st.id = r.shop_table_id " +
            "where shop_id = :shopId and r.reservation_date = :date and r.reservation_time = :time and st.max_people = :people) "
            +
            "and max_people = :time", nativeQuery = true)
    ShopTable findByDataAndTimeAndPeople(@Param("shopId") Long shopId, @Param("date") String date,
            @Param("time") String time, @Param("people") int people);

    // -----------------------------------------------------------------------------------------

    @Query(value = "select max_people as maxPeople, count(max_people) qty from shop_table group by max_people", nativeQuery = true)
    List<QtyTableDto> findQtyTable();

    @Query(value = "select max_people as maxPeople, count(max_people) qty from shop_table group by max_people having max_people=:people", nativeQuery = true)
    QtyTableDto findQtyTableByNum(@Param("people") int people);

}