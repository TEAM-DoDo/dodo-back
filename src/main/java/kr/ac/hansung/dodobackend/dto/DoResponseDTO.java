package kr.ac.hansung.dodobackend.dto;

import lombok.*;

@Getter
@Builder
public class DoResponseDTO {
    private Long id;
    private String name;
    private String description;
    private String place;
    private String bannerImagepath;
}
