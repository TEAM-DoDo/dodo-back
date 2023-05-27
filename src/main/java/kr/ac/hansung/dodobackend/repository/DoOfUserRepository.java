package kr.ac.hansung.dodobackend.repository;

import kr.ac.hansung.dodobackend.entity.Do;
import kr.ac.hansung.dodobackend.entity.DoOfUser;
import kr.ac.hansung.dodobackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface DoOfUserRepository extends JpaRepository<DoOfUser, Long> {
    List<DoOfUser> findByUser(User user); //유저가 속한 DO 찾을때 사용
    @Query("select distinct dou.user from DoOfUser dou where dou.myDo.id = :doId")
    List<User> findUserListInDoByDoId(@Param("doId") Long doId);
}
