package site.metacoding.finals.domain.review;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import site.metacoding.finals.dto.review.ReviewRespDto;
import site.metacoding.finals.dto.test.testDto;

@Repository
@RequiredArgsConstructor
public class ReviewQueryRepository {

    private final EntityManager em;

    public ReviewRespDto findByAvgScore(Long shopId) {

        String sql = "select avg(r.score) score from review r ";
        sql += "where shop_id=:shopId";

        JpaResultMapper jpaResultMapper = new JpaResultMapper();
        Query q = em.createNativeQuery(sql)
                .setParameter("shopId", shopId);

        ReviewRespDto result = jpaResultMapper.uniqueResult(q, ReviewRespDto.class);

        return result;
    }
}
