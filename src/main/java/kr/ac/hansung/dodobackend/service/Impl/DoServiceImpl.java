package kr.ac.hansung.dodobackend.service.Impl;

import kr.ac.hansung.dodobackend.dto.NoticeDTO;
import kr.ac.hansung.dodobackend.dto.PostDTO;
import kr.ac.hansung.dodobackend.entity.Do;
import kr.ac.hansung.dodobackend.entity.Notice;
import kr.ac.hansung.dodobackend.entity.Post;
import kr.ac.hansung.dodobackend.repository.DoRepository;
import kr.ac.hansung.dodobackend.repository.NoticeRepository;
import kr.ac.hansung.dodobackend.repository.PostRepository;
import kr.ac.hansung.dodobackend.service.DoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DoServiceImpl implements DoService {
    private final DoRepository doRepository;
    private final PostRepository postRepository;
    private final ImageServiceImpl imageServiceImpl;
    private final NoticeRepository noticeRepository;
    private static int imageNumber = 0;

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
