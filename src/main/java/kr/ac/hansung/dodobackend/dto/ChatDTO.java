package kr.ac.hansung.dodobackend.dto;

import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatDTO {
    private long id;
    private long userId; //작성자
    private long username;
    private String date;
    private String content; //내용
}
