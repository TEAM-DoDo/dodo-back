package kr.ac.hansung.dodobackend.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = "myDo")
@Table(name = "schedule")
@Entity
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; //기본키

    @Column(name = "title")
    private String title; //제목
    @Column(name = "date")
    private String date; //날짜
    @Column(name = "startTime")
    private String startTime; //시작시간
    @Column(name = "endTime")
    private String endTime; //종료시간
    @Column(name = "place")
    private String place; //장소
    @Column(name = "detail")
    private String detail; //상세정보

    //이 일정이 소속된 커뮤니티의 기본키를 외래키로 받음
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "do_id")
    private Do myDo;

    @Builder
    public Schedule(String title, String date, String startTime, String endTime,
                    String place, String detail, Do myDo)
    {
        this.title = title;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.place = place;
        this.detail = detail;
        this.myDo = myDo;
    }
}
