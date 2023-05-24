package kr.ac.hansung.dodobackend.repository;

import kr.ac.hansung.dodobackend.entity.Do;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface DoRepository extends JpaRepository<Do, Long> {
    @Query("select id from Do")
    List<Long> getAllCommunityId();
}
