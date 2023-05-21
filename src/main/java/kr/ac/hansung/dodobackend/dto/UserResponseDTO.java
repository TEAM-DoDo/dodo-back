package kr.ac.hansung.dodobackend.dto;

import kr.ac.hansung.dodobackend.entity.Community;
import kr.ac.hansung.dodobackend.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class UserResponseDTO { //유저 조회 결과를 반환하는 DTO
    private final User user;
    private final List<Community> communityList = new ArrayList<>();
}