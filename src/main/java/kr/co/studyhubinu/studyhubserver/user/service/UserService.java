package kr.co.studyhubinu.studyhubserver.user.service;

import kr.co.studyhubinu.studyhubserver.user.domain.UserEntity;
import kr.co.studyhubinu.studyhubserver.user.dto.data.GeneralSignUpInfo;
import kr.co.studyhubinu.studyhubserver.user.exception.UserException;
import kr.co.studyhubinu.studyhubserver.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static kr.co.studyhubinu.studyhubserver.user.exception.UserErrorCode.USER_NOT_FOUND_EXCEPTION;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void registerUser(GeneralSignUpInfo signUpInfo) {
        log.info(signUpInfo.getEmail());
        if (userRepository.existsByEmail(signUpInfo.getEmail())) {
            throw new UserException(USER_NOT_FOUND_EXCEPTION);
        }

        UserEntity userEntity = signUpInfo.changeEntity(bCryptPasswordEncoder);

        userRepository.save(userEntity);
    }

}
