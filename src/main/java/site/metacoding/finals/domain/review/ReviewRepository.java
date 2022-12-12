package site.metacoding.finals.domain.review;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import site.metacoding.finals.dto.repository.customer.ScoreRespDto;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByCustomerId(Long customerId);

    List<Review> findByShopId(Long shopId);

    // //
    @Query(value = "select avg(r.score) score from review r where shop_id=:shopId", nativeQuery = true)
    ScoreRespDto findByAvgScore(@Param("shopId") Long shopId);

}
