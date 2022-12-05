package site.metacoding.finals.domain.shop_table;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ShopTableRepository extends JpaRepository<ShopTable, Long> {

    List<ShopTable> findByShopId(Long shopId);

    @Query(value = "select * from shop_table where shop_id= ?1 and max_people= ?2", nativeQuery = true)
    public List<ShopTable> findByMaxPeople(Long id, int maxPeople);

    @Query(value = "select * from shop_table where shop_id= ?1 and max_people= ?2 AND is_active = 1"
            + " order by id asc limit 1", nativeQuery = true)
    public ShopTable findByMaxPeopleToMinId(Long id, int maxPeople);

    @Query(value = "select distinct(max_people) from shop_table where shop_id= ?1", nativeQuery = true)
    Optional<List<Integer>> findDistinctByShopId(Long id);

}