package kr.ac.hansung.dodobackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/status")
public class ServerStatusController {
    //서버가 켜져있는지 알려주는 RestApi
    @GetMapping
    public ResponseEntity<?> getServerStatus(){
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
