package kr.ac.hansung.dodobackend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Table(name = "scheduleOfUser")
@Entity
public class ScheduleOfUser {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
