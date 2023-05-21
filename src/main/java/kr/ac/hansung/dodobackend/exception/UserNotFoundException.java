package kr.ac.hansung.dodobackend.exception;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true) //final 필드를 초기화 할 수 없는 기본생성자를 통해 인스턴스를 만들경우, force=true옵션을 통해 final 필드를 0/false/null로 초기화
public class UserNotFoundException extends RuntimeException {
    private final int code;
    private final String message; //에러 메세지

    @Builder
    private UserNotFoundException(int code, String message) {
        this.code = code;
        this.message = message;
    }
}

