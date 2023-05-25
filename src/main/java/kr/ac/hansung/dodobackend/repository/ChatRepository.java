package kr.ac.hansung.dodobackend.repository;

import kr.ac.hansung.dodobackend.entity.Chat;
import kr.ac.hansung.dodobackend.entity.Do;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findByMyDo(Do findedDo);
}
