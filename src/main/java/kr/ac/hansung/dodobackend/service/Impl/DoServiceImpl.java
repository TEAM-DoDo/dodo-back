package kr.ac.hansung.dodobackend.service.Impl;

import kr.ac.hansung.dodobackend.dto.DoEnterDTO;
import kr.ac.hansung.dodobackend.dto.NoticeDTO;
import kr.ac.hansung.dodobackend.dto.PostDTO;
import kr.ac.hansung.dodobackend.entity.Do;
import kr.ac.hansung.dodobackend.entity.Notice;
import kr.ac.hansung.dodobackend.entity.Post;
import kr.ac.hansung.dodobackend.entity.Schedule;
import kr.ac.hansung.dodobackend.exception.FileNotFoundException;
import kr.ac.hansung.dodobackend.exception.ScheduleNotFoundException;
import kr.ac.hansung.dodobackend.exception.UserNotFoundException;
import kr.ac.hansung.dodobackend.repository.DoRepository;
import kr.ac.hansung.dodobackend.repository.NoticeRepository;
import kr.ac.hansung.dodobackend.repository.PostRepository;
import kr.ac.hansung.dodobackend.repository.ScheduleRepository;
import kr.ac.hansung.dodobackend.service.DoOfUserService;
import kr.ac.hansung.dodobackend.service.DoService;
import kr.ac.hansung.dodobackend.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.json.HTTP;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DoServiceImpl implements DoService {
    private final DoRepository doRepository;
    private final PostRepository postRepository;
    private final ImageServiceImpl imageServiceImpl;
    private final NoticeRepository noticeRepository;
    private final ScheduleRepository scheduleRepository;
    private final ImageService imageService;
    private final DoOfUserService doOfUserService;
    private static int imageNumber = 0;

    @Override
    public List<Long> getAllCommunityId()
    {
        List<Long> idList = doRepository.getAllCommunityId();
        return idList;
    }

    @Override
    public void createNewDo(Map<String,Object> result) {
        //받아온 데이터를 새로운 커뮤니티로 생성
        String name = result.get("doName").toString();
        String place = result.get("place").toString();
        String description = result.get("description").toString();
        Do doInfo = Do.builder().name(name).description(description).place(place).bannerImagepath("").build();
        Do createdDo = doRepository.save(doInfo);

        String userId = result.get("userId").toString();
        DoEnterDTO doEnterDTO = new DoEnterDTO();
        doEnterDTO.setDoId(createdDo.getId());
        doEnterDTO.setUserId(Long.valueOf(userId));
        doEnterDTO.setHostTrue(true);
        doOfUserService.CreateDoOfUser(doEnterDTO);
    }
    @Override
    public Do getDo(int doId) {
        Do data = doRepository.findById((long) doId).orElse(null);
        if (data == null){
            String errorMessage = "Received unknown do id.";
            throw UserNotFoundException.builder().code(HttpStatus.BAD_REQUEST.value()).message(errorMessage).build();
        }
        return data;
    }

    @Override
    public Schedule getDoSchedule(Long doId)
    {
       Schedule schedule = scheduleRepository.findFirstByMyDo_IdOrderByStartTime(doId);
        if (schedule == null){
            String errorMessage = "Received unknown do id.";
            throw ScheduleNotFoundException.builder().code(HttpStatus.BAD_REQUEST.value()).message(errorMessage).build();
        }

       return schedule;
    }

    @Override
    public void uploadTitleImage(int doId, List<MultipartFile> files)
    {
        String imageSavedFolderName = "/" + doId + "/banners/";
        String imageName = files.get(0).getOriginalFilename();
        String path = imageService.putFile(imageSavedFolderName, files.get(0), imageName);
        Optional<Do> findedDo = doRepository.findById(Long.valueOf(doId));
        Do myDo = findedDo.get();
        myDo.UpdateBannerImage(path);
        doRepository.save(myDo);
    }

    @Override
    public File getTitleImage(int doId)
    {
        String imageSavedFolderName = "/" + doId + "/banners/";
        String imagePath = doRepository.findById(Long.valueOf(doId)).get().getBannerImagepath();
        //imagePath = imagePath.contains(".jpeg") ? imagePath :  imagePath + ".jpeg";
        File file = imageService.getFile(imageSavedFolderName, imagePath);
        if(file.exists() == false)
        {
            String errorMessage = "Title image File을 찾을 수 없습니다.";
            throw FileNotFoundException.builder().code(HttpStatus.NOT_FOUND.value()).message(errorMessage).build();
        }
        return file;
    }


    @Override
    public List<Post> GetPostsByDoId(Long doId)
    {
        Optional<Do> findedDo = doRepository.findById(doId);

        List<Post> posts = postRepository.findByMyDo(findedDo.get());

        return posts;
    }

    @Override
    public List<Notice> GetNoticeByDoId(Long doId)
    {
        Optional<Do> findedDo = doRepository.findById(doId);

        List<Notice> notices = noticeRepository.findByMyDo(findedDo.get());

        return notices;
    }

    @Override
    public void CreatePost(Long doId, PostDTO postDTO)
    {
        Optional<Do> findedDo = doRepository.findById(doId);
        String imageSavedFolderName = "/posts/";
        String imageName = String.valueOf(++imageNumber);
        String imagePath = imageServiceImpl.putFile(imageSavedFolderName, postDTO.getFiles().get(0), imageName);
        Post newPost = Post.builder().imagePath(imagePath).myDo(findedDo.get()).build();
        postRepository.save(newPost);
    }

    @Override
    public void CreateNotice(Long doId, NoticeDTO noticeDTO)
    {
        Optional<Do> findedDo = doRepository.findById(doId);
        String title = noticeDTO.getTitle();
        String writtenBy = noticeDTO.getWrittenBy();
        String content = noticeDTO.getContent();
        String reportingDate = noticeDTO.getReportingDate();

        Notice newNotice = Notice.builder().title(title).writtenBy(writtenBy).content(content)
                .reportingDate(reportingDate).myDo(findedDo.get()).build();
        noticeRepository.save(newNotice);
    }
}
