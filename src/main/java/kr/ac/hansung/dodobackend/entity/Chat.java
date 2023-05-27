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
    @Column(name = "user_id")
    private Long userId; //작성자
    @Column(name = "do")
    private Long doId;//작성한 장소
    @Column(name = "time")
    private String date; //작성일
    @Column(name = "content",length = 512)
    private String content; //내용

    
    //소속된 커뮤니티의 기본키를 외래키로 가짐
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "do_id")
    private Do myDo;

    @Builder
    private Chat(long userId,long doId, String date, String content)
    {
        this.userId = userId;
        this.doId = doId;
        this.date = date;
        this.content = content;
    }
}
