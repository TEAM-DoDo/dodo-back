package kr.ac.hansung.dodobackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostDTO {
//    @NotBlank(message = "imagePath is blank")
//    private String imagePath;

    @NotNull(message = "image file data is null")
    private List<MultipartFile> files;
}
