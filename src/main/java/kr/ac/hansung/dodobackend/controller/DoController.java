package kr.ac.hansung.dodobackend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.ac.hansung.dodobackend.entity.Community;
import kr.ac.hansung.dodobackend.entity.Notice;
import kr.ac.hansung.dodobackend.entity.Schedule;
import kr.ac.hansung.dodobackend.repository.CommunityRepository;
import kr.ac.hansung.dodobackend.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/do")
public class DoController {
    private Map<Integer, Community> doDummy = new HashMap<>();
    @Autowired
    ImageService imageService;
    @Autowired
    CommunityRepository communityRepository;
    @GetMapping("/list")
    public ResponseEntity<Map<String,Object>> getDoIDList(){
        Map<String,Object> result = new HashMap<>();
        //현재는 받아온 키셋을 넘겨 보내도록 하고 있음 향후 쿼리로 변경하여 바로 리스트를 받아와 전송하도록 하면 됨
        result.put("do_id",communityRepository.getAllCommunityId());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @PostMapping("/create")
    public ResponseEntity<?> createDO(@Validated @RequestBody String json){
        Map<String,Object> result;
        try{
            result = new ObjectMapper().readValue(json, HashMap.class);
        } catch (JsonProcessingException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        //받아온 데이터를 새로운 커뮤니티로 생성
        String name = result.get("doName").toString();
        String place = result.get("place").toString();
        String description = result.get("description").toString();
        Community doInfo = Community.builder().name(name).description(description).place(place).bannerImagepath("").build();
        communityRepository.save(doInfo);

//        doInfo.setImage(result.get("image").toString());
        //현재는 더미에 데이터를 저장하도록 되어있음
//        doDummy.put(doDummy.size(),doInfo);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/{do_id}")
    public ResponseEntity<Community> getDo(@PathVariable("do_id") int doId){
        Community data = communityRepository.findById((long) doId).orElse(null);
        if (data == null){
            System.out.println("Received unknown do id.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    //가장 최근의 스케줄을 사용자에게 전송해주는 코드
    @GetMapping("/{do_id}/schedule")
    public ResponseEntity<Schedule> getDoSchedule(@PathVariable("do_id") int doId){
        Community doData = doDummy.get(doId);
        if (doData == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        var schedules = doData.getSchedules();
        if (schedules.isEmpty()){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        var schedule = schedules.get(schedules.size()-1);
        return new ResponseEntity<>(schedule,HttpStatus.OK);
    }
    @PostMapping("/{do_id}/schedule/create")
    public ResponseEntity<?> createDoSchedule(@PathVariable("do_id") int doId,@Validated @RequestBody String json) {
        Map<String, Object> result;
        try {
            result = new ObjectMapper().readValue(json, HashMap.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/{do_id}/title-image")
    public ResponseEntity<Resource> uploadTitleImage(@PathVariable("do_id") int doId, List<MultipartFile> files){
        if (files.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(imageService.putFile("/title/",files.get(0),Integer.toString(doId)) == null){
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    //타이틀 이미지 사진
    @GetMapping("/{do_id}/title-image")
    public ResponseEntity<Resource> uploadTitleImage(@PathVariable("do_id") int doId){
        System.out.println("Request do title image : " + doId);
        var file = imageService.getFile("/title/",Integer.toString(doId)+ ".jpeg");
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
    //공지사항을 추가하는 코드
    @PostMapping("/{do_id}/notice")
    public ResponseEntity<?> postNewNotice(@PathVariable("do_id") int doId, @Validated @RequestBody Notice notice){
        //어드민인지 확인하는 코드
        boolean isAdmin = true;
        if (!isAdmin){
            return new ResponseEntity<>("{\"message\" : \"Permissions not allowed.\"}",HttpStatus.BAD_REQUEST);
        }
        Community data = doDummy.get(doId);
        if (data == null){
            return new ResponseEntity<>("{\"message\" : \"Do for this id does not exist.\"}",HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
