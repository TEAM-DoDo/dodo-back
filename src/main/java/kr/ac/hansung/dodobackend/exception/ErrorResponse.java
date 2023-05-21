package kr.ac.hansung.dodobackend.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponse {
    private final String message;
    private final int code;
}
