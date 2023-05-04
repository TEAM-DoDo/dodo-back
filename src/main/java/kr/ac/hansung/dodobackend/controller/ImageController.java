package kr.ac.hansung.dodobackend.controller;

import kr.ac.hansung.dodobackend.model.User;
import kr.ac.hansung.dodobackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value="/api/image")
public class ImageController {
    private final String path = "./image/";
    public String getFullPath(String filename) { return path + filename; }
    //이미지 데이터 베이스 구축 전까지 임시로 이미지 주고 받음

    public ResponseEntity<?> test(){
        return new ResponseEntity<>("{}", HttpStatus.ACCEPTED);
    }
    @PostMapping
    public ResponseEntity<?> UploadsImage(@RequestParam("file") MultipartFile file)
    {
        if (file.isEmpty()){
            return new ResponseEntity<>("{}",HttpStatus.OK);
        }
        String orgFileName = file.getOriginalFilename();
        String storeFileName = String.format("%d",(new Date()).getTime());
        try{
            file.transferTo(new File(getFullPath(storeFileName)));
        } catch (IOException e){
            return new ResponseEntity<>("{}",HttpStatus.OK);
        }

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

}
