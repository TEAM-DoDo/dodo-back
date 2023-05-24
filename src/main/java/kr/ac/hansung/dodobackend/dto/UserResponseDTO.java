package kr.ac.hansung.dodobackend.dto;

import kr.ac.hansung.dodobackend.entity.Do;
import kr.ac.hansung.dodobackend.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class UserResponseDTO { //유저 조회 결과를 반환하는 DTO
    private final User user;
    private final List<Do> doList = new ArrayList<>();
}
