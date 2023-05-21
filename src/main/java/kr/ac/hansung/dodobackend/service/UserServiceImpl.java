package kr.ac.hansung.dodobackend.service;

import kr.ac.hansung.dodobackend.dto.SignUpDTO;
import kr.ac.hansung.dodobackend.dto.SignUpResponseDTO;
import kr.ac.hansung.dodobackend.dto.UserResponseDTO;
import kr.ac.hansung.dodobackend.entity.User;
import kr.ac.hansung.dodobackend.exception.UserNotFoundException;
import kr.ac.hansung.dodobackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor //생성자 주입
public class UserServiceImpl implements UserService{ //유저 서비스 레이어
    private final UserRepository userRepository; //생성자 주입

    @Override
    public UserResponseDTO GetUserById(Long id) {
        //아이디로 조회
        Optional<User> user = userRepository.findById(id);

        //만약 아이디에 해당하는 유저가 없다면 에러처리
        if(user.isPresent() == false)
        {
            System.out.println("isPresent()로 예외 처리 감지");
            String errorMessage = "해당 유저를 찾을 수 없습니다.";
            throw UserNotFoundException.builder().code(HttpStatus.NOT_FOUND.value()).message(errorMessage).build();
            //throw 시 메서드의 실행이 중지되어, 아래 코드는 실행되지 않음
        }

        //반환
        UserResponseDTO userResponseDTO = UserResponseDTO.builder().user(user.get()).build();
        return userResponseDTO;
    }

    @Override
    public UserResponseDTO GetUserByNickname(String nickname) {
        //조회
        User user = userRepository.findByNickname(nickname);

        //만약 아이디에 해당하는 유저가 없다면 에러처리
        if(user == null)
        {
            System.out.println("닉네임 조회 예외 처리 감지");
            String errorMessage = "해당 유저를 찾을 수 없습니다.";
            throw UserNotFoundException.builder().code(HttpStatus.NOT_FOUND.value()).message(errorMessage).build();
            //throw 시 메서드의 실행이 중지되어, 아래 코드는 실행되지 않음
        }

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
