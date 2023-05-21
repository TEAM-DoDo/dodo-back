package kr.ac.hansung.dodobackend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED) //@Requestbody는 기본생성자가 반드시 필요하다. 따라서 접근자가 protected인 기본생성자 만듬. 외부에서 접근 불가. 내부와 jpa만 접근가능
public class SignUpDTO { //클라이언트로부터 날라온 회원 가입 정보를 담는 DTO. 기본생성자를 써야하기에 final 키워드는 사용불가.
    @NotBlank(message = "Phone Number is blank") //null이면 안되고, 공백제외 최소 문자 1개이상이 존재해야함
    private String phoneNumber; //핸드폰 번호
    @NotBlank(message = "nickname is blank")
    private String nickname; //유저 닉네임
    @NotBlank(message = "Birth of date is blank")
    private String dateOfBirth; //생년월일
    @NotBlank(message = "Address is blank")
    private String address; //주소
    @NotBlank(message = "Gender is blank")
    private String gender; //성별

    //카테고리 추후 추가할 것.
}
