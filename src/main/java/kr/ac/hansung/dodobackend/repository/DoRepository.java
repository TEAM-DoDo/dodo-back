package kr.ac.hansung.dodobackend.repository;

import kr.ac.hansung.dodobackend.entity.Do;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface DoRepository extends JpaRepository<Do, Long> {
    @Query("select d.id from Do d")
    List<Long> getAllCommunityId();
    @Query("select d.id from Do d where d.name like %:searchName% and d.description like %:searchDes% and d.place like %:searchPlace%")
    List<Long> getDoIdListBySearch(@Param("searchName") String searchName,@Param("searchDes") String searchDes,@Param("searchPlace")String searchPlace);
}
