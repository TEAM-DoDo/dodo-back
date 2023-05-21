package kr.ac.hansung.dodobackend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignInDTO {
    @NotBlank(message="phoneNumber is blank")
    private String phoneNumber;
}
