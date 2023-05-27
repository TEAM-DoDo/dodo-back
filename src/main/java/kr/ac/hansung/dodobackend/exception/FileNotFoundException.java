package kr.ac.hansung.dodobackend.exception;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class FileNotFoundException extends RuntimeException{
    private final int code;
    private final String message;

    @Builder
    private FileNotFoundException(int code, String message)
    {
        this.code = code;
        this.message = message;
    }
}
