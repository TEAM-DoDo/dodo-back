package kr.ac.hansung.dodobackend.repository;

import kr.ac.hansung.dodobackend.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ChatRepository extends JpaRepository<Chat, Long> {

}