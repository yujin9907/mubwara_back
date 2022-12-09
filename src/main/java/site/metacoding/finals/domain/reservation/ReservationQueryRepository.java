package site.metacoding.finals.domain.reservation;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transaction;

import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import site.metacoding.finals.dto.repository.shop.AnalysisDto;
import site.metacoding.finals.dto.test.testDto;

@Repository
@RequiredArgsConstructor
public class ReservationQueryRepository {

    private final EntityManager em;

    public List<testDto> findByTest() {

        JpaResultMapper jpaResultMapper = new JpaResultMapper();
        Query q = em.createNativeQuery("select id, content as name from review");
        List<testDto> list = jpaResultMapper.list(q, testDto.class);

        return list;
    }

}
