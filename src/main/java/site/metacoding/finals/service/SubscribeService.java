package site.metacoding.finals.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import site.metacoding.finals.config.annotation.VerifyShop;
import site.metacoding.finals.config.auth.PrincipalUser;
import site.metacoding.finals.domain.customer.Customer;
import site.metacoding.finals.domain.customer.CustomerRepository;
import site.metacoding.finals.domain.shop.Shop;
import site.metacoding.finals.domain.shop.ShopRepository;
import site.metacoding.finals.domain.subscribe.Subscribe;
import site.metacoding.finals.domain.subscribe.SubscribeRepository;
import site.metacoding.finals.dto.subscribe.SubscribeRespDto.SubscribeSaveRespDto;

@Service
@RequiredArgsConstructor
public class SubscribeService {

        private final ShopRepository shopRepository;
        private final SubscribeRepository subscribeRepository;
        private final CustomerRepository customerRepository;

        // @VerifyShop // principal로 shop id를 검증하는 게 아니라서 안 됨
        @Transactional
        public SubscribeSaveRespDto save(PrincipalUser principalUser, Long ShopId) {
                // 사용자 검증
                Shop shopPS = shopRepository.findById(ShopId)
                                .orElseThrow(() -> new RuntimeException("존재하지 않는 가게입니다"));

                // 구독하기
                Subscribe subscribe = subscribeRepository
                                .save(new Subscribe(null, principalUser.getCustomer(), shopPS));

                return new SubscribeSaveRespDto(subscribe);
        }

        @Transactional
        public void delete(Long subscribeId, PrincipalUser principalUser) {
                // 사용자 검증
                Subscribe subscribePS = subscribeRepository.findById(subscribeId)
                                .orElseThrow(() -> new RuntimeException("존재하지 않는 구독 내역입니다"));

                // 구독취소하기
                subscribeRepository.delete(subscribePS);

        }

}
