package kr.ac.hansung.dodobackend.controller;

import kr.ac.hansung.dodobackend.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@RestController
@RequestMapping(value="/api/image")
public class ImageController {
    @Autowired
    ImageService imageService;
    private final String imagePath = "/images/";
    //현재 저장되어 있는 이미지의 갯수를 전송해줌
    @GetMapping("/download/{dodoId}/length")
    public ResponseEntity<?> getImageLength(@PathVariable int dodoId){
        int result = imageService.getLength(Integer.toString(dodoId));
        if (result < 0){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        //이미지를 가져오는 알고리즘 작성
        return new ResponseEntity<>("{"+result+"}",HttpStatus.OK);
    }
    //이미지 이름을 받아 다운로드 하는 함수 향후 아이디를 받아 다운로드 하도록 만들 예정
    @GetMapping("/download/{dodoId}/{imageName}")
    public ResponseEntity<Resource> getImage(@PathVariable int dodoId,@PathVariable String imageName){
        System.out.println("Image request from dodo id : " + dodoId + " / image name : " + imageName);
        var file = imageService.getFile(Integer.toString(dodoId),imageName);
        if (file == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        FileSystemResource result = new FileSystemResource(file);
        HttpHeaders header = new HttpHeaders();
        try {
            header.add("Content-Type", Files.probeContentType(file.toPath()));
        } catch (IOException e) {
            System.out.println("Can't add header");
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        //이미지를 가져오는 알고리즘 작성
        return new ResponseEntity<>(result,header,HttpStatus.OK);
    }
    //이미지 이름 리스트를 가져오는 함수, 향후 아이디를 가져오는 함수로 변경 예정
    @GetMapping("/download/{dodoId}/list")
    ResponseEntity<Map<String,Object>> getImageIdList(@PathVariable int dodoId){
        Map<String,Object> result = new HashMap<>();
        List<String> imageId = imageService.getImageIdList(Integer.toString(dodoId));
        result.put("image_id",imageId);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
    @PostMapping("/upload/{dodoId}")
    public ResponseEntity<?> UploadsImage(@PathVariable int dodoId,@RequestPart List<MultipartFile> files)
    {
        boolean isFileUploaded = false;
        try {
            isFileUploaded = imageService.putFiles(dodoId,files);
        }catch (IOException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INSUFFICIENT_STORAGE);
        }
        if (!isFileUploaded){
            return new ResponseEntity<>(HttpStatus.INSUFFICIENT_STORAGE);
        }
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }
}
