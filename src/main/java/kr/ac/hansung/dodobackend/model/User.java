package kr.ac.hansung.dodobackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Table(name = "user")
@Entity
public class User{
    @Id
    private String nickname;

    @Column(name = "birth")
    private String dateOfBirth;

    @Column(name = "address")
    private String address;

    @Column(name="gener")
    private String gender;

    @Column(name="level")
    private String level;

    @Column(name="badge")
    private String badge;

    @Column(name="schedule")
    private String schedule;

    @Column(name="profileImagePath")
    private String profileImagePath;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notice> notices = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<InterestOfUser> interestOfUserList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<CommunityOfUser> communityOfUserList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<ScheduleOfUser> scheduleOfUserList = new ArrayList<>();
}