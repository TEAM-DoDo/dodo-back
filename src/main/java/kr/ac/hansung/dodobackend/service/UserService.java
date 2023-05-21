package kr.ac.hansung.dodobackend.service;

import kr.ac.hansung.dodobackend.dto.SignUpDTO;
import kr.ac.hansung.dodobackend.dto.SignUpResponseDTO;
import kr.ac.hansung.dodobackend.dto.UserResponseDTO;

public interface UserService {
    SignUpResponseDTO SignUp(SignUpDTO signUpDTO); //유저 신규 생성

    UserResponseDTO GetUserByNickname(String nickname); //닉네임으로 유저 조회
}
