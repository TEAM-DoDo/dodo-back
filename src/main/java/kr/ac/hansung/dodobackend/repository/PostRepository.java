package kr.ac.hansung.dodobackend.repository;

import kr.ac.hansung.dodobackend.entity.Do;
import kr.ac.hansung.dodobackend.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByMyDo(Do findedDo);
}
