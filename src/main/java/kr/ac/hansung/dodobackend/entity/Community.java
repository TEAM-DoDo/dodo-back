package kr.ac.hansung.dodobackend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = "")
@Table(name="community")
@Entity
public class Community {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private Long id;

    @Column(name="name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "place")
    private String place;
    @Column(name="bannerImagePath")
    private String bannerImagepath;

    //공지사항
    @OneToMany(mappedBy="community", cascade = CascadeType.ALL, orphanRemoval = true) //영속성 연계와 고아객체 자동삭제.
    private List<Notice> notices = new ArrayList<>();

    //작성글
    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    //일정
    @OneToMany(mappedBy = "community")
    private List<Schedule> schedules = new ArrayList<>();

    //채팅
    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Chat> chats = new ArrayList<>();

    @Builder
    private Community(String name, String description, String place, String bannerImagepath)
    {
        this.name = name;
        this.description = description;
        this.place = place;
        this.bannerImagepath = bannerImagepath;
    }
}
