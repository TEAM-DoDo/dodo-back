package kr.ac.hansung.dodobackend.service;

import kr.ac.hansung.dodobackend.dto.NoticeDTO;
import kr.ac.hansung.dodobackend.dto.PostDTO;
import kr.ac.hansung.dodobackend.entity.Do;
import kr.ac.hansung.dodobackend.entity.Notice;
import kr.ac.hansung.dodobackend.entity.Post;
import kr.ac.hansung.dodobackend.entity.Schedule;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface DoService {
    List<Long> getAllCommunityId();
    void createNewDo(Map<String,Object> result);
    Do getDo(int doId);
    Schedule getDoSchedule(Long doId);

    List<Post> GetPostsByDoId(Long doId);
    List<Notice> GetNoticeByDoId(Long doId);
    void CreatePost(Long doId, PostDTO postDTO);
    void CreateNotice(Long doId, NoticeDTO noticeDTO);
    void uploadTitleImage(int doId, List<MultipartFile> files);
    File getTitleImage(int doId);
}
