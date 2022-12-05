package site.metacoding.finals.domain.reservation;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transaction;

import lombok.RequiredArgsConstructor;
import site.metacoding.finals.dto.repository.shop.AnalysisDto;

interface Dao {
    List<AnalysisDto> findBySumDateTest(Long shopId, String date);
}

@RequiredArgsConstructor
public class ReservationRepositoryImpl implements Dao {

    private final EntityManager em;

    @Override
    public List<AnalysisDto> findBySumDateTest(Long shopId, String date) {

        // String sql = "select r.reservation_time time, st.max_people people,
        // s.per_price price from reservation r ";
        // sql += "inner join shop_table st on r.shop_table_id = st.id ";
        // sql += "inner join shop s on st.shop_id = s.id ";
        // sql += "where s.id=:shopId and r.reservation_date=:date ";

        // TypedQuery<AnalysisDto> query = em.createQuery(sql, AnalysisDto.class);
        // query.setParameter("shopId", shopId);
        // query.setParameter("date", date);

        // return query.getResultList();

        return null;
    }

}
