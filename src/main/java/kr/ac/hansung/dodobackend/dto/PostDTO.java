package kr.ac.hansung.dodobackend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostDTO {
    @NotBlank(message = "imagePath is blank")
    private String imagePath;
}
