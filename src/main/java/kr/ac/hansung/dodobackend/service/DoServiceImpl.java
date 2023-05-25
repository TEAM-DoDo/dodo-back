package kr.ac.hansung.dodobackend.service;

import kr.ac.hansung.dodobackend.dto.NoticeDTO;
import kr.ac.hansung.dodobackend.dto.PostDTO;
import kr.ac.hansung.dodobackend.entity.Chat;
import kr.ac.hansung.dodobackend.entity.Do;
import kr.ac.hansung.dodobackend.entity.Notice;
import kr.ac.hansung.dodobackend.entity.Post;
import kr.ac.hansung.dodobackend.exception.UserNotFoundException;
import kr.ac.hansung.dodobackend.repository.ChatRepository;
import kr.ac.hansung.dodobackend.repository.DoRepository;
import kr.ac.hansung.dodobackend.repository.NoticeRepository;
import kr.ac.hansung.dodobackend.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DoServiceImpl implements DoService{
    private final DoRepository doRepository;
    private final PostRepository postRepository;
    private final ChatRepository chatRepository;
    private final NoticeRepository noticeRepository;

    public List<Post> GetPostsByDoId(Long doId)
    {
        Optional<Do> findedDo = doRepository.findById(doId);

        List<Post> posts = postRepository.findByMyDo(findedDo.get());

        return posts;
    }

    public List<Notice> GetNoticeByDoId(Long doId)
    {
        Optional<Do> findedDo = doRepository.findById(doId);

        List<Notice> notices = noticeRepository.findByMyDo(findedDo.get());

        return notices;
    }

    public void CreatePost(Long doId, PostDTO postDTO)
    {
        Optional<Do> findedDo = doRepository.findById(doId);
        Post newPost = Post.builder().imagePath(postDTO.getImagePath()).myDo(findedDo.get()).build();
        postRepository.save(newPost);
    }

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
