package kr.ac.hansung.dodobackend.service.Impl;

import kr.ac.hansung.dodobackend.dto.DoEnterDTO;
import kr.ac.hansung.dodobackend.dto.ScheduleDTO;
import kr.ac.hansung.dodobackend.entity.Do;
import kr.ac.hansung.dodobackend.entity.User;
import kr.ac.hansung.dodobackend.repository.UserRepository;
import kr.ac.hansung.dodobackend.service.DoOfUserService;
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
    private final DoOfUserService doOfUserService;
    public void createDummy(){
        //유저 더미데이터 생성 방법
        //첫번째 생성 친구
        User user201 =
                User.builder()
                        .phoneNumber("01019279473")
                        .category("[\"globe\",\"people-circle]")
                        .dateOfBirth("2000-01-01")
                        .gender("남").level(0)
                        .nickname("한수민")
                        .address("서울 노원구 동일로245길 162 은빛1단지아파트").build();
        userRepository.save(user201);
        //첫번째 생성 친구
        User user202 =
                User.builder()
                        .phoneNumber("010-1234-5678")
                        .category("[\"book\",\"game-controller\"]")
                        .dateOfBirth("2002-01-01")
                        .gender("여").level(0)
                        .nickname("홍화영")
                        .address("서울 도봉구").build();
        userRepository.save(user202);
        //첫번째 생성 친구
        User user203 =
                User.builder()
                        .phoneNumber("010-2684-6248")
                        .category("[\"globe\",\"game-controller\"]")
                        .dateOfBirth("1998-02-01")
                        .gender("남").level(0)
                        .nickname("김군용")
                        .address("서울 관악구 이곳 저곳").build();
        userRepository.save(user203);

        //첫번째 생성 친구
        User user204 =
                User.builder()
                        .phoneNumber("010-5876-3412")
                        .category("[\"globe\",\"game-controller\"]")
                        .dateOfBirth("1998-02-01")
                        .gender("남").level(0)
                        .nickname("김군용")
                        .address("서울 관악구 이곳 저곳").build();
        userRepository.save(user204);

        //첫번째 생성 친구
        User user205 =
                User.builder()
                        .phoneNumber("010-1324-4326")
                        .category("[\"globe\",\"game-controller\"]")
                        .dateOfBirth("1998-02-01")
                        .gender("남").level(0)
                        .nickname("김군용")
                        .address("서울 관악구 이곳 저곳").build();
        userRepository.save(user205);

        //첫번째 생성 친구
        User user206 =
                User.builder()
                        .phoneNumber("010-5313-1465")
                        .category("[\"globe\",\"game-controller\"]")
                        .dateOfBirth("2002-03-06")
                        .gender("남").level(0)
                        .nickname("하진수")
                        .address("서울시 용산구 아이파크").build();
        userRepository.save(user206);

        //친구 더미 생성
        User user207 =
                User.builder()
                        .phoneNumber("01027489341")
                        .category("[\"globe\",\"game-controller\" ,\"Korea\",\"Korea\",\"Student\"]")
                        .dateOfBirth("1999-07-27")
                        .gender("남").level(0)
                        .nickname("심수민")
                        .address("성남시 분당구 판교로").build();
        userRepository.save(user207);

        User user208 =
                User.builder()
                        .phoneNumber("01023847384")
                        .category("[\"globe\",\"game-controller\" ,\"Korea\",\"Badminton\",\"Student\"]")
                        .dateOfBirth("1987-03-24")
                        .gender("남").level(0)
                        .nickname("김세훈")
                        .address("성남시 분당구 황새울로").build();
        userRepository.save(user208);

        User user209 =
                User.builder()
                        .phoneNumber("01023847384")
                        .category("[\"airplane\",\"beer\" ,\"Korea\",\"Seller\",\"Flex\"]")
                        .dateOfBirth("1999-04-25")
                        .gender("여").level(0)
                        .nickname("김수현")
                        .address("성남시 분당구 백현로").build();
        userRepository.save(user209);

        User user210 =
                User.builder()
                        .phoneNumber("01012341234")
                        .category("[\"beer\",\"paw\" ,\"Korea\",\"Photo\",\"Doctor\"]")
                        .dateOfBirth("1999-03-15")
                        .gender("남").level(0)
                        .nickname("장예찬")
                        .address("성남시 분당구 판교로").build();
        userRepository.save(user210);

        User user211 =
                User.builder()
                        .phoneNumber("01011112222")
                        .category("[\"restaurant\",\"ㅔㅁㅈ\" ,\"Korea\",\"Doctor\",\"Dog\"]")
                        .dateOfBirth("1987-06-08")
                        .gender("여").level(0)
                        .nickname("박경주")
                        .address("성남시 분당구 판교로").build();
        userRepository.save(user211);

        //Do 더미 생성
        //첫 번째 생성
        Map<String,Object> do101 = new HashMap<>();
        do101.put("doName","같이 여행다녀요"); // 두 이름
        do101.put("place","서울시 관악구"); // 두 위치
        do101.put("description","서울의 이곳저곳을 여행다니는 모임이에요 다들 친절하시니 같이 다녀요."); // 두 설명
        do101.put("userId",1);//생성한 유저의 고유 번호
        doService.createNewDo(do101);

        Map<String,Object> do102 = new HashMap<>();
        do102.put("doName","롯데백화점 수호대"); // 두 이름
        do102.put("place","서울시 노원구"); // 두 위치
        do102.put("description","노원구 롯데백화점에서 종종 하루 종일 돌아다니는 모임이에요."); // 두 설명
        do102.put("userId",1);//생성한 유저의 고유 번호
        doService.createNewDo(do102);

        Map<String,Object> do103 = new HashMap<>();
        do103.put("doName","같이 여행다녀요"); // 두 이름
        do103.put("place","서울시 관악구"); // 두 위치
        do103.put("description","서울의 이곳저곳을 여행다니는 모임이에요 다들 친절하시니 같이 다녀요."); // 두 설명
        do103.put("userId",1);//생성한 유저의 고유 번호
        doService.createNewDo(do103);

//        //Do Schedule 더미 생성
//        //첫 번째 생성
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setCost("10000");//두 비용
        scheduleDTO.setDoId(1L);//생성한 Do의 고유 번호
        scheduleDTO.setDoId(1L);//생성한 Do의 고유 번호

        //Do 사용자 참여 참여 생성하는 방법
        DoEnterDTO doEnterDTO = new DoEnterDTO();
        doEnterDTO.setDoId(1L);//두 ID 기입
        doEnterDTO.setUserId(1L);//사용자 아이디 기입
        doOfUserService.CreateDoOfUser(doEnterDTO);

    }
}
