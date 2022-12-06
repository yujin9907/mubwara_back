package site.metacoding.finals.config.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import site.metacoding.finals.domain.user.User;
import site.metacoding.finals.domain.user.UserRepository;

@Service
@RequiredArgsConstructor
public class PrincipalService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User userPS = userRepository.findByUsername(username);

        if (userPS != null) {
            return new PrincipalUser(userPS);
        }
        return null;
    }

}
