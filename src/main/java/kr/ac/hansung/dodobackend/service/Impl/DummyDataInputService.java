package kr.ac.hansung.dodobackend.service.Impl;

import kr.ac.hansung.dodobackend.entity.Do;
import kr.ac.hansung.dodobackend.service.DoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class DummyDataInputService {
    private final DoService doService;
    public void createDummy(){
        //Do 더미 생성
        Map<String,Object> result = new HashMap<>();
        result.put("doName","테스트");
        result.put("place","테스트");
        result.put("description","테스트");
        result.put("userId",1);
        doService.createNewDo(result);
    }
}
