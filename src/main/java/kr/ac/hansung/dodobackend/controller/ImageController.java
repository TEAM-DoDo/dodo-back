package kr.ac.hansung.dodobackend.controller;

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
    private final String imagePath = "/images/";
    //현재 저장되어 있는 이미지의 갯수를 전송해줌
    @GetMapping("/download/{dodoId}/length")
    public ResponseEntity<?> getImageLength(@PathVariable int dodoId){
        Path folderPath = CreatePath(imagePath+"/"+dodoId);
        if (folderPath==null){
            System.out.println("Failed to create path");
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);//저장곤간이 생성되지 않음
        }
        var files = (new File(folderPath.toUri())).listFiles();
        if (files == null){
            System.out.println("No files found in path : " + folderPath);
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        //이미지를 가져오는 알고리즘 작성
        return new ResponseEntity<>("{"+files.length+"}",HttpStatus.OK);
    }
    //이미지 이름을 받아 다운로드 하는 함수 향후 아이디를 받아 다운로드 하도록 만들 예정
    @GetMapping("/download/{dodoId}/{imageName}")
    public ResponseEntity<Resource> getImage(@PathVariable int dodoId,@PathVariable String imageName){
        System.out.println("Image request from dodo id : " + dodoId + " / image name : " + imageName);
        var path = imagePath+"/"+dodoId;
        if (!checkPath(path)){
            System.out.println("test");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);//저장곤간이 생성되지 않음
        }
        Path folderPath = CreatePath(path);
        if (folderPath==null){
            System.out.println("Failed to create path");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        var file = new File(folderPath+"/"+imageName);
        if (!file.exists()){
            System.out.println("File not exist[path : " + file.getAbsolutePath() + "]");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
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
        var path = imagePath+"/"+dodoId;
        if (!checkPath(path)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);//저장곤간이 생성되지 않음
        }
        Path folderPath = CreatePath(path);
        Map<String,Object> result = new HashMap<>();
        if (folderPath==null){
            System.out.println("Failed to create path");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);//저장곤간이 생성되지 않음
        }
        var files = (new File(folderPath.toUri())).listFiles();
        if (files == null){
            System.out.println("No files found in path : " + folderPath);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<String> imageId = new ArrayList<>();
        for (var file : files){
            imageId.add(file.getName());
        }
        result.put("image_id",imageId);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
    @PostMapping("/upload/{dodoId}")
    public ResponseEntity<?> UploadsImage(@PathVariable int dodoId,@RequestPart List<MultipartFile> files)
    {
        if (files.size() == 0){
            return new ResponseEntity<>(HttpStatus.OK);//저장곤간이 생성되지 않음
        }
        //do 가 존재하는지 확인하는 무결성 코드 필요
        Path storagePath = CreatePath(imagePath);
        if (storagePath==null){
            return new ResponseEntity<>(HttpStatus.INSUFFICIENT_STORAGE);//저장곤간이 생성되지 않음
        }
        Path folderPath = CreatePath(imagePath+"/"+dodoId);
        if (folderPath==null){
            return new ResponseEntity<>(HttpStatus.INSUFFICIENT_STORAGE);//저장곤간이 생성되지 않음
        }
        //파일을 순회하면 데이터베이스에 기입
        for(var file : files){
            //데이터 저장 경로 알고리즘 제작 필요
            if (file == null){
                System.out.println("파일 null");
                return new ResponseEntity<>("{}",HttpStatus.NOT_ACCEPTABLE);
            }
            if (file.isEmpty()){
                System.out.println("파일 empty");
                return new ResponseEntity<>("{}",HttpStatus.NOT_ACCEPTABLE);
            }
            String orgFileName = file.getOriginalFilename();
            String storeFileName = String.format("%d",(new Date()).getTime());
            System.out.println(orgFileName);
            System.out.println(file.getContentType());
            Path path = Paths.get(folderPath+ "/" +orgFileName).toAbsolutePath();
            try{
                System.out.println(path);
                file.transferTo(path.toFile());
            } catch (IOException e){
                e.printStackTrace();
                return new ResponseEntity<>("{}",HttpStatus.NOT_ACCEPTABLE);
            }
            System.out.println("저장됨");
        }
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }
    //파일 경로가 없으면 생성하고 있다면 파일 경로를 생성하여 돌려주는 함수
    private boolean checkPath(String path){
        Path source = null;
        try {
            source = Paths.get(this.getClass().getResource("/").toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();//
            return false;
        }
        Path dirPath = Paths.get(source.toAbsolutePath()+path);
        return Files.exists(dirPath);
    }
    private Path CreatePath(String path){
        Path source = null;
        try {
            source = Paths.get(this.getClass().getResource("/").toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();//
            return null;
        }
        Path dirPath = Paths.get(source.toAbsolutePath()+path);
        if (Files.exists(dirPath)){
            return dirPath;
        }
        try{
            Files.createDirectories(dirPath);
            System.out.println("Directory not found, create file : " + source.toAbsolutePath()+path);
            return dirPath;
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}

