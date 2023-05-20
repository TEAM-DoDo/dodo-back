package kr.ac.hansung.dodobackend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) //접근자가 protected인 기본 생성자
@ToString(exclude = "") //무한루프를 방지하기 위해 연관관계를 맺은 테이블은 제외
@Table(name = "user") //db에 테이블 이름을 정하기. 이 어노테이션 안쓰면 클래스 이름과 동일한 테이블명을 가짐
@Entity
public class User{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) //아이디 생성전략을 db에 의존함
    private Long id; //기본키. 생성전략이 있으므로 이 값은 빌더 패턴에 의해 할당되지 않아야한다.

    @Column(name = "phoneNumber")
    private String phoneNumber;
    @Column(name = "nickname") //db에 칼럼 이름을 정하기. 이 어노테이션 안쓰면 필드 이름과 동일한 칼럼명을 가짐
    private String nickname;
    @Column(name = "dateOfBirth")
    private String dateOfBirth;
    @Column(name = "address")
    private String address;
    @Column(name="gender")
    private String gender;
    @Column(name="level")
    private int level;
    @Column(name="badge")
    private String badge;
    @Column(name="schedule")
    private String schedule;
    @Column(name="profileImagePath")
    private String profileImagePath;

    @Builder //id를 제외한 나머지 필드들을 매개변수로 받는 빌더패턴에 사용될 생성자
    private User(String phoneNumber, String nickname, String dateOfBirth, String address, String gender,
                 int level, String badge, String schedule, String profileImagePath)
    {
        this.phoneNumber = phoneNumber;
        this.nickname = nickname;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.gender = gender;
        this.level = level;
        this.badge = badge;
        this.schedule = schedule;
        this.profileImagePath = profileImagePath;
    }

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Notice> notices = new ArrayList<>();
//
//    @OneToMany(mappedBy = "user")
//    private List<InterestOfUser> interestOfUserList = new ArrayList<>();
//
//    @OneToMany(mappedBy = "user")
//    private List<CommunityOfUser> communityOfUserList = new ArrayList<>();
//
//    @OneToMany(mappedBy = "user")
//    private List<ScheduleOfUser> scheduleOfUserList = new ArrayList<>();

}