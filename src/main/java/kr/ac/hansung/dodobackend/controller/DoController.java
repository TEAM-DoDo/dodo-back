package kr.ac.hansung.dodobackend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import kr.ac.hansung.dodobackend.dto.NoticeDTO;
import kr.ac.hansung.dodobackend.dto.PostDTO;
import kr.ac.hansung.dodobackend.dto.ScheduleDTO;
import kr.ac.hansung.dodobackend.entity.*;
import kr.ac.hansung.dodobackend.repository.DoRepository;
import kr.ac.hansung.dodobackend.repository.ScheduleRepository;
import kr.ac.hansung.dodobackend.service.DoService;
import kr.ac.hansung.dodobackend.service.ImageService;
import kr.ac.hansung.dodobackend.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/do")
@RequiredArgsConstructor
public class DoController {
    private Map<Integer, Do> doDummy = new HashMap<>();
    private final DoService doService;
    private final ScheduleService scheduleService;

    @GetMapping("/list")
    public ResponseEntity<Map<String,Object>> getDoIDList()
    {
        Map<String,Object> result = new HashMap<>();
        List<Long> communityIdList = doService.getAllCommunityId();
        //현재는 받아온 키셋을 넘겨 보내도록 하고 있음 향후 쿼리로 변경하여 바로 리스트를 받아와 전송하도록 하면 됨
        result.put("do_id", communityIdList);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createDO(@Valid @RequestBody String json)
    {
        Map<String,Object> result;
        try{
            result = new ObjectMapper().readValue(json, HashMap.class);
        } catch (JsonProcessingException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        doService.createNewDo(result);

//        doInfo.setImage(result.get("image").toString());
        //현재는 더미에 데이터를 저장하도록 되어있음
//        doDummy.put(doDummy.size(),doInfo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{do_id}")
    public ResponseEntity<Do> getDo(@PathVariable("do_id") int doId)
    {
        Do data = doService.getDo(doId);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    //가장 최근의 스케줄을 사용자에게 전송해주는 코드
    @GetMapping("/{do_id}/schedules")
    public ResponseEntity<?> getDoSchedule(@PathVariable("do_id") Long doId)
    {
        Schedule result = doService.getDoSchedule(doId);
        if (result == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @PostMapping("/{do_id}/schedules")
    public ResponseEntity<String> createSchedule(@Valid @RequestBody ScheduleDTO scheduleDTO) //스케쥴 전용 dto..
    {
        //출력
        System.out.println("클라이언트가 전송한 일정 개설 정보 : " + scheduleDTO);
        //서비스 레이어에 전달
        scheduleService.CreateSchedule(scheduleDTO);
        return new ResponseEntity<>("새로운 일정 생성 성공", HttpStatus.CREATED);
    }

    @PostMapping("/{do_id}/title-image")
    public ResponseEntity<Resource> uploadTitleImage(@PathVariable("do_id") int doId, List<MultipartFile> files)
    {
        if (files.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        doService.uploadTitleImage(doId, files);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //타이틀 이미지 사진
    @GetMapping("/{do_id}/title-image")
    public ResponseEntity<Resource> getTitleImage(@PathVariable("do_id") int doId) {
        System.out.println("Request do title image : " + doId);
        File file = doService.getTitleImage(doId);
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
        return new ResponseEntity<>(result, header, HttpStatus.OK);
    }

    @GetMapping("/{do_id}/posts")
    public List<Post> GetPostsByDoId(@PathVariable("do_id") Long id)
    {
        System.out.println("do 아이디로 게시글 조회, 입력받은 아이디 : " + id);
        List<Post> posts = doService.GetPostsByDoId(id);
        return posts;
    }

    @GetMapping("/{do_id}/notices")
    public List<Notice> GetNoticesByDoId(@PathVariable("do_id") Long id)
    {
        System.out.println("do 아이디로 공지 조회, 입력받은 아이디 : " + id);
        List<Notice> notices = doService.GetNoticeByDoId(id);
        return notices;
    }

    @PostMapping("/{do_id}/posts")
    public ResponseEntity<String> CreatePost(@PathVariable("do_id") Long id, @Valid @RequestBody PostDTO postDTO)
    {
        System.out.println("do 아이디로 게시글 생성, 입력받은 아이디 : " + id);
        doService.CreatePost(id, postDTO);
        return new ResponseEntity<>("게시글 생성 성공", HttpStatus.CREATED);
    }

    @PostMapping("/{do_id}/notices")
    public ResponseEntity<String> CreateNotice(@PathVariable("do_id") Long id, @Valid @RequestBody NoticeDTO noticeDTO)
    {
        System.out.println("do 아이디로 공지글 생성, 입력받은 아이디 : " + id);
        doService.CreateNotice(id, noticeDTO);
        return new ResponseEntity<>("공지글 생성 성공", HttpStatus.CREATED);
    }
}
