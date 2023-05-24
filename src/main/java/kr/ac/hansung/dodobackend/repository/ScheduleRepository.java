package kr.ac.hansung.dodobackend.repository;

import kr.ac.hansung.dodobackend.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
