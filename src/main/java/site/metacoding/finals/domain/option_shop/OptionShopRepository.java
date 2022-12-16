package site.metacoding.finals.domain.option_shop;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import site.metacoding.finals.domain.shop.Shop;

public interface OptionShopRepository extends JpaRepository<OptionShop, Long> {

    void deleteByShopId(Long shopId);
}
