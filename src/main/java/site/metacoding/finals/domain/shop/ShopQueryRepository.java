package site.metacoding.finals.domain.shop;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import site.metacoding.finals.dto.repository.shop.PopularListRespDto;
import site.metacoding.finals.dto.shop.ShopRespDto.ReservationShopRespDto;

@Repository
@RequiredArgsConstructor
public class ShopQueryRepository {

    private final EntityManager em;

    public List<PopularListRespDto> findOptionListByOptionId(List<Long> opitonIds) {

        String query = "select s.id shopId, s.shop_name shopName, s.address, s.category, i.store_filename storeFileName, ";
        query += "s.open_time openTime, s.close_time closeTime, s.phone_number phoneNumber, count(os.id) count ";
        query += "from shop s";
        query += "left join image_file i on s.id = i.shop_id ";
        query += "left join (select * from option_shop o where o.option_id=1) os on s.id = os.shop_id "; // 여기가 웨어 절이
                                                                                                         // 돌아야 됨

        String innerQuery = "";
        for (Long id : opitonIds) {
            // innerQuery =
        }

        query += "group by s.id order by count desc";

        return null;
    }

    public List<ReservationShopRespDto> findResevationByCustomerId(Long customerId) {

        String sql = "select i.store_filename as storeFilename, r4.shop_name as shopName, r4.address as address, r4.category as category, ";
        sql += "r4.reservation_time as reservationTime, r4.reservation_date as reservationDate ";
        sql += "from image_file i ";
        sql += "right join (select shop.id, shop.shop_name, shop.address, shop.category, r3.reservation_date, r3.reservation_time from shop ";
        sql += "right join (select st.shop_id, r2.* from shop_table st ";
        sql += "right join (select r.* from reservation r where customer_id= :customerId) r2 ";
        sql += "on st.id = r2.shop_table_id) r3 ";
        sql += "on shop.id=r3.shop_id) r4 ";
        sql += "on r4.id=i.shop_id";

        JpaResultMapper jpaResultMapper = new JpaResultMapper();
        Query q = em.createNativeQuery(sql)
                .setParameter("customerId", customerId);

        List<ReservationShopRespDto> result = jpaResultMapper.list(q, ReservationShopRespDto.class);

        return result;
    }
}
