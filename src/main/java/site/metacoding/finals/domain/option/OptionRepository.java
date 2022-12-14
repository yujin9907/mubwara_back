package site.metacoding.finals.domain.option;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OptionRepository extends JpaRepository<Option, Long> {

    // @EntityGraph(attributePaths = "imageFile")
    @Query("select o from Option o join OptionShop os on o=os.option where os.shop.id = :shopId")
    List<Option> findByShopId(@Param("shopId") Long shopId);
}
