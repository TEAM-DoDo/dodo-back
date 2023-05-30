package kr.ac.hansung.dodobackend.repository;

import kr.ac.hansung.dodobackend.entity.Schedule;
import kr.ac.hansung.dodobackend.entity.ScheduleOfUser;
import kr.ac.hansung.dodobackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ScheduleOfUserRepository extends JpaRepository<ScheduleOfUser, Long> {
    List<ScheduleOfUser> findByUser(User user);
    @Query("select distinct sou.user from ScheduleOfUser sou where sou.schedule.id = :scheduleId")
    List<User> findUserListInScheduleByScheduleId(long scheduleId);
    @Query("select  distinct sou.schedule from ScheduleOfUser sou where sou.schedule.id =:userId")
    List<Schedule> findScheduleListByUserId(long userId);
}
