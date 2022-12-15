package site.metacoding.finals.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import site.metacoding.finals.domain.customer.Customer;
import site.metacoding.finals.domain.shop.Shop;
import site.metacoding.finals.domain.user.User;
import site.metacoding.finals.domain.user.UserRepository;

@Getter
@RequiredArgsConstructor
public class PrincipalUser implements UserDetails {

    private final User user;
    private final Long id;
    private final Customer customer;
    private final Shop shop;

    public PrincipalUser(User user) {
        this.user = user;
        this.id = user.getId();
        this.customer = user.getCustomer();
        this.shop = user.getShop();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new SimpleGrantedAuthority(user.getRole().name()));
        return collection;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    // 계정 만료 여부
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 잠금 계정 여부
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 비밀번호 변경 기한 만료
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정 활성화 여부
    @Override
    public boolean isEnabled() {
        return true;
    }

}
