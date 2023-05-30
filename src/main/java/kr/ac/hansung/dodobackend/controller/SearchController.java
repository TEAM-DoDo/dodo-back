package kr.ac.hansung.dodobackend.controller;

import kr.ac.hansung.dodobackend.entity.User;
import kr.ac.hansung.dodobackend.service.DoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/search")//토큰 필요
@RequiredArgsConstructor
public class SearchController {
    private final DoService doService;
    @GetMapping("/do")
    public ResponseEntity<?> searchDoByName(@RequestParam("name")String name,@RequestParam("description")String description,@RequestParam("place") String place,@RequestParam("category")String category){
        List<Long> result = doService.searchDoByInfo(name,description,place,category);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
