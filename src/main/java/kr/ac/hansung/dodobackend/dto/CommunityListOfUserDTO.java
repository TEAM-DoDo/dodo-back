package kr.ac.hansung.dodobackend.dto;

import kr.ac.hansung.dodobackend.entity.Community;
import kr.ac.hansung.dodobackend.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CommunityListOfUserDTO { //유저가 참가한 커뮤니티 리스트를 반환하는 DTO
    private final User user;
    private final List<Community> communityList;
}
