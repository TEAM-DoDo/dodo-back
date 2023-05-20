package kr.ac.hansung.dodobackend.service;

import kr.ac.hansung.dodobackend.dto.SignUpDTO;
import kr.ac.hansung.dodobackend.dto.SignUpResponseDTO;
import kr.ac.hansung.dodobackend.dto.UserResponseDTO;
import kr.ac.hansung.dodobackend.entity.User;
import kr.ac.hansung.dodobackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor //생성자 주입
public class UserServiceImpl implements UserService{ //유저 서비스 레이어
    private final UserRepository userRepository; //생성자 주입

    @Override
    public UserResponseDTO GetUserByNickname(String nickname) {
        //조회
        User user = userRepository.findByNickname(nickname);

        //반환
        UserResponseDTO userResponseDTO = UserResponseDTO.builder().user(user).build();
        return userResponseDTO;
    }

    @Override
    public SignUpResponseDTO SignUp(SignUpDTO signUpDTO) {
        //DTO를 Entity로 변환
        User newUser = User.builder().phoneNumber(signUpDTO.getPhoneNumber()).nickname(signUpDTO.getNickname()).dateOfBirth(signUpDTO.getDateOfBirth())
                .address(signUpDTO.getAddress()).gender(signUpDTO.getGender())
                .level(1).badge("몸소 행차하신분").schedule("일정 없음").profileImagePath("파이어스토어 주소").build();

        //Entity 저장
        userRepository.save(newUser);

        //검색
        User user = userRepository.findByNickname(signUpDTO.getNickname());

        //반환
        SignUpResponseDTO signUpResponseDTO = SignUpResponseDTO.builder().phoneNumber(user.getPhoneNumber()).nickname(user.getNickname())
                .dateOfBirth(user.getDateOfBirth()).address(user.getAddress()).gender(user.getGender()).statusForTest("새로운 유저 가입 완료!").build();
        return signUpResponseDTO;
    }
}
