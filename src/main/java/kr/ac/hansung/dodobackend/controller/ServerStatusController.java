package kr.ac.hansung.dodobackend.controller;

import kr.ac.hansung.dodobackend.service.Impl.DummyDataInputService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/status")//토큰 불필요
public class ServerStatusController {
    private final DummyDataInputService dummyDataInputService;
    private boolean isDummyInputted = false;
    //서버가 켜져있는지 알려주는 RestApi
    @GetMapping
    public ResponseEntity<?> getServerStatus(){
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/test")
    public ResponseEntity<?> getServerStatusTest(){
        if (!isDummyInputted){
            dummyDataInputService.createDummy();
            isDummyInputted = true;
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
