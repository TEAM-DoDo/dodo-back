package kr.ac.hansung.dodobackend.repository;

import kr.ac.hansung.dodobackend.entity.Do;
import kr.ac.hansung.dodobackend.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByMyDo(Do findedDo);

    @Query("select distinct p.id from Post p join p.myDo where p.myDo.id = :doId") //do하고 post join
    List<Long> findPostIdsByDoId(@Param("doId") Long doId);

}
