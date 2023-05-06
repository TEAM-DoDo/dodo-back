package kr.ac.hansung.dodobackend.dao;

import kr.ac.hansung.dodobackend.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ChatDao extends JpaRepository<Chat,Integer> {
}
