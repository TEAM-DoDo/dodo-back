package kr.ac.hansung.dodobackend.dto;

import kr.ac.hansung.dodobackend.entity.Do;
import kr.ac.hansung.dodobackend.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString(exclude = "doList")
public class DoListOfUserDTO { //유저가 참가한 커뮤니티 리스트를 반환하는 DTO
    private final Long userId;
    private final List<DoResponseDTO> doResponseDTOList;
}
