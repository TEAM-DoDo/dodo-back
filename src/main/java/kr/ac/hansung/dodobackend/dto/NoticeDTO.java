package kr.ac.hansung.dodobackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoticeDTO {
    @NotBlank(message = "title is blank")
    private String title;
    @NotBlank(message = "writtenBy is blank")
    private String writtenBy;
    @NotBlank(message = "content is blank")
    private String content;
    @NotBlank(message = "reportingDate is blank")
    private String reportingDate;
}
