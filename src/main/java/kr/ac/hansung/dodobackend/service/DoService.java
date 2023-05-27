package kr.ac.hansung.dodobackend.service;

import kr.ac.hansung.dodobackend.dto.NoticeDTO;
import kr.ac.hansung.dodobackend.dto.PostDTO;
import kr.ac.hansung.dodobackend.entity.Notice;
import kr.ac.hansung.dodobackend.entity.Post;

import java.util.List;

public interface DoService {
    List<Post> GetPostsByDoId(Long doId);
    List<Notice> GetNoticeByDoId(Long doId);
    void CreatePost(Long doId, PostDTO postDTO);
    void CreateNotice(Long doId, NoticeDTO noticeDTO);
}
