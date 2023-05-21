package kr.ac.hansung.dodobackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileImageDTO {
    @NotBlank(message = "id is blank")
    private Long id;
    @NotNull(message = "image file data is null")
    private List<MultipartFile> files;
}
