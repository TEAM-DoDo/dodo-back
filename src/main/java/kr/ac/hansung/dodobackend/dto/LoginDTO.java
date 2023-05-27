package kr.ac.hansung.dodobackend.dto;

import kr.ac.hansung.dodobackend.entity.User;
import kr.ac.hansung.dodobackend.jwt.TokenInfo;
import lombok.*;

@Getter
@Setter
@ToString
public class LoginDTO {
    private User userdata;
    private TokenInfo tokenInfo;
}
