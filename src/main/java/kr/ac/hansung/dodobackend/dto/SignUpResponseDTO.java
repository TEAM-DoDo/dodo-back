package kr.ac.hansung.dodobackend.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder //빌더 패턴 적용
public class SignUpResponseDTO { //회원 가입 정상 처리 후 신규 가입자의 정보를 반환하는 DTO입니다.
    private final String phoneNumber; //핸드폰 번호
    private final String nickname; //유저 닉네임
    private final String dateOfBirth; //생년월일
    private final String address; //주소
    private final String gender; //성별
    private final String category; //관심사 카테고리
    private final String statusForTest; //값이 잘 들어왔는지 확인하는 용도. 나중에 삭제할 것.
}
