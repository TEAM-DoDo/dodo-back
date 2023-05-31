package kr.ac.hansung.dodobackend.service.Impl;

import kr.ac.hansung.dodobackend.entity.Do;
import kr.ac.hansung.dodobackend.entity.User;
import kr.ac.hansung.dodobackend.repository.UserRepository;
import kr.ac.hansung.dodobackend.service.DoService;
import kr.ac.hansung.dodobackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class DummyDataInputService {
    private final DoService doService;
    private final UserRepository userRepository;
    public void createDummy(){
        //유저 더미데이터 생성 방법
        //첫번째 생성 친구
        User user = 
                User.builder()
                        .phoneNumber("010-1234-5678")
                        .category("[\"book\"\\,\"people-circle]")
                        .dateOfBirth("2000-01-01")
                        .gender("남").level(0)
                        .nickname("테스트")
                        .address("서울 노원구 동일로245길 162 은빛1단지아파트").build();
        userRepository.save(user);

        
        //Do 더미 생성
        Map<String,Object> result = new HashMap<>();
        result.put("doName","테스트"); // 두 이름
        result.put("place","테스트"); // 두 위치
        result.put("description","테스트"); // 두 설명
        result.put("userId",1);//생성한 유저의 고유 번호

        doService.createNewDo(result);
    }
}
