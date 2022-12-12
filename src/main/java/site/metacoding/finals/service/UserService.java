package site.metacoding.finals.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.finals.config.jwt.JwtProcess;
import site.metacoding.finals.domain.user.User;
import site.metacoding.finals.domain.user.UserRepository;
import site.metacoding.finals.dto.user.UserReqDto.JoinReqDto;
import site.metacoding.finals.dto.user.UserRespDto.JoinRespDto;

@Service
@RequiredArgsConstructor
public class UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public String checkUsername(String username) {
        User userPS = userRepository.findByUsername(username);
        if (userPS == null) {
            return "ok";
        }
        return "중복";
    }

    @Transactional
    public JoinRespDto join(JoinReqDto joinReqDto) {
        String encPassword = bCryptPasswordEncoder.encode(joinReqDto.getPassword());
        joinReqDto.setPassword(encPassword);

        User userPS = userRepository.save(joinReqDto.toEntity());

        // userPS값을 바로 return하면 Entity에 영향이 가나?
        return new JoinRespDto(userPS);
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id).get();
    }

}
