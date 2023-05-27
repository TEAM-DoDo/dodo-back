package kr.ac.hansung.dodobackend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@ToString(exclude = {"notices", "posts", "schedules", "chats"})
@ToString
@Table(name="do")
@Entity
public class Do {
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

    public void UpdateBannerImage(String path)
    {
        this.bannerImagepath = path;
    }

    //공지사항
    //@OneToMany(mappedBy="myDo", cascade = CascadeType.ALL, orphanRemoval = true) //영속성 연계와 고아객체 자동삭제.
    //private List<Notice> notices = new ArrayList<>();

    //작성글
    //@OneToMany(mappedBy = "myDo", cascade = CascadeType.ALL, orphanRemoval = true)
    //private List<Post> posts = new ArrayList<>();

    //일정
    //@OneToMany(mappedBy = "myDo")
    //rivate List<Schedule> schedules = new ArrayList<>();

    //채팅
    //@OneToMany(mappedBy = "myDo", cascade = CascadeType.ALL, orphanRemoval = true)
    //private List<Chat> chats = new ArrayList<>();

    @Builder
    private Do(String name, String description, String place, String bannerImagepath)
    {
        this.name = name;
        this.description = description;
        this.place = place;
        this.bannerImagepath = bannerImagepath;
    }
}
