package kr.ac.hansung.dodobackend.repository;

import kr.ac.hansung.dodobackend.entity.CommunityOfUser;
import kr.ac.hansung.dodobackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface CommunityOfUserRepository extends JpaRepository<CommunityOfUser, Long> {
    List<CommunityOfUser> findByUser(User user);
}
