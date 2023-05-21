package kr.ac.hansung.dodobackend.repository;

import kr.ac.hansung.dodobackend.entity.Chat;
import kr.ac.hansung.dodobackend.entity.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface CommunityRepository extends JpaRepository<Community, Long> {
    @Query("select id from Community")
    List<Long> getAllCommunityId();
}
