package kr.ac.hansung.dodobackend.repository;

import kr.ac.hansung.dodobackend.entity.DoOfUser;
import kr.ac.hansung.dodobackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface DoOfUserRepository extends JpaRepository<DoOfUser, Long> {
    List<DoOfUser> findByUser(User user);
}
