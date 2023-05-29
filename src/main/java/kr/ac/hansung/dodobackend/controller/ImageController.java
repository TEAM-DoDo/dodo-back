package kr.ac.hansung.dodobackend.controller;

import kr.ac.hansung.dodobackend.entity.Do;
import kr.ac.hansung.dodobackend.entity.Post;
import kr.ac.hansung.dodobackend.repository.DoRepository;
import kr.ac.hansung.dodobackend.repository.PostRepository;
import kr.ac.hansung.dodobackend.service.Impl.ImageServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

@RestController
@RequestMapping(value="/api/image")
@RequiredArgsConstructor
public class ImageController {
    private final ImageServiceImpl imageServiceImpl;
    private final PostRepository postRepository;
    private final DoRepository doRepository;

    //현재 저장되어 있는 이미지의 갯수를 전송해줌
    @GetMapping("/download/{dodoId}/length")
    public ResponseEntity<?> getImageLength(@PathVariable int dodoId){
        int result = imageServiceImpl.getLength(Integer.toString(dodoId));
        if (result < 0){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        //이미지를 가져오는 알고리즘 작성
        return new ResponseEntity<>("{"+result+"}",HttpStatus.OK);
    }
    //이미지 이름을 받아 다운로드 하는 함수 향후 아이디를 받아 다운로드 하도록 만들 예정
    @GetMapping("/download/{dodoId}/{imageId}")
    public ResponseEntity<Resource> getImage(@PathVariable("dodoId") int dodoId,@PathVariable("imageId") Long imageId){
        System.out.println("Image request from dodo id : " + dodoId + " / image name : " + imageId);
        Optional<Post> post = postRepository.findById(imageId);
        String imageSavedFolderName = "/" + dodoId + "/posts/";
        var file = imageServiceImpl.getFile(imageSavedFolderName,post.get().getImagePath());
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
        //Optional<Do> findedDo = doRepository.findById(Long.valueOf(dodoId));
        List<Long> postsIdList = postRepository.findPostIdsByDoId(Long.valueOf(dodoId));

        Map<String,Object> result = new HashMap<>();
        List<String> imageId = imageServiceImpl.getImageIdList(Integer.toString(dodoId));
        //result.put("image_id",imageId);
        result.put("image_id",postsIdList);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
    @PostMapping("/upload/{dodoId}")
    public ResponseEntity<?> UploadsImage(@PathVariable Long dodoId,@RequestPart List<MultipartFile> files)
    {
        boolean isFileUploaded = false;
        try {
//            isFileUploaded = imageService.putFiles(String.valueOf(dodoId),files);
            String imageSavedFolderName = "/" + dodoId + "/posts/";
            String imagePath = imageServiceImpl.putFile(imageSavedFolderName, files.get(0), files.get(0).getOriginalFilename());
            if(imagePath.length() > 0) isFileUploaded = true;
            Optional<Do> findedDo = doRepository.findById(dodoId);
            Post newPost = Post.builder().imagePath(imagePath).myDo(findedDo.get()).build();
            postRepository.save(newPost);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INSUFFICIENT_STORAGE);
        }
        if (!isFileUploaded){
            return new ResponseEntity<>(HttpStatus.INSUFFICIENT_STORAGE);
        }
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

}
