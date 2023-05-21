package kr.ac.hansung.dodobackend.service;

import kr.ac.hansung.dodobackend.dto.*;

public interface UserService {
    UserResponseDTO GetUserById(Long id); //아이디로 유저 조회
    UserResponseDTO GetUserByNickname(String nickname); //닉네임으로 유저 조회
    SignUpResponseDTO SignUp(SignUpDTO signUpDTO); //유저 신규 생성
    UserResponseDTO GetUserByPhoneNumber(String phoneNumber); //핸드폰번호로 유저 조회
    UserResponseDTO SignIn(SignInDTO signInDTO); //로그인
    UserResponseDTO changeProfileImage(ProfileImageDTO profileImageDTO); //프로필 이미지 업데이트
}
