package site.metacoding.finals.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.metacoding.finals.config.enums.Role;
import site.metacoding.finals.domain.customer.Customer;
import site.metacoding.finals.domain.user.User;

public class CustomerReqDto {

    @Setter
    @Getter
    public static class CustomerSaveReqDto {
        private String name;
        private String phoneNumber;
        private String address;

        public Customer toEntity(User user) {
            return Customer.builder()
                    .name(name)
                    .phoneNumber(phoneNumber)
                    .address(address)
                    .user(user)
                    .build();
        }
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class CustomerJoinReqDto {
        private String name;
        private String phoneNumber;
        private String address;
        private String username;
        private String password;

        public Customer toCustomerEntity(User user) {
            return Customer.builder()
                    .name(this.name)
                    .phoneNumber(this.phoneNumber)
                    .address(this.address)
                    .user(user)
                    .isDeleted(false)
                    .build();
        }

        public User toUserEntity() {
            return User.builder()
                    .username(this.username)
                    .password(this.password)
                    .role(Role.USER)
                    .isDeleted(false)
                    .build();
        }
    }

    @Getter
    @Setter
    public static class CustomerUpdateReqDto {
        private String name;
        private String address;
        private String phoneNumber;
    }

}
