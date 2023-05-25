package kr.ac.hansung.dodobackend.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = "myDo")
@Table(name = "chat")
@Entity
public class Chat {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; //기본키

    @Column(name = "nickname")
    private String nickname; //작성자
    @Column(name = "time")
    private String reportingDate; //작성일
    @Column(name = "content")
    private String content; //내용
    @Column(name="profileImagePath")
    private String profileImagePath; //작성자의 프로필 이미지 경로

    //소속된 커뮤니티의 기본키를 외래키로 가짐
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "do_id")
    private Do myDo;

    @Builder
    private Chat(String nickname, String reportingDate, String content, String profileImagePath)
    {
        this.nickname = nickname;
        this.reportingDate = reportingDate;
        this.content = content;
        this.profileImagePath = profileImagePath;
    }

    public void setTime(int time) {
        this.reportingDate = Integer.toString(time);
    }
}
