package kr.ac.hansung.dodobackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@ToString
public class ProfileImageDTO {
    @NotBlank(message = "id is blank")
    private Long id;
    @NotNull(message = "image file data is null")
    private List<MultipartFile> files;
}
