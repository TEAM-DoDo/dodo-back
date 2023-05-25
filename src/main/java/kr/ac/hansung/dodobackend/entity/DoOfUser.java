package kr.ac.hansung.dodobackend.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"user", "myDo"}) //연관관계 필드가 여러개라면 중괄호 사용
@Table(name = "doOfUser")
@Entity
public class DoOfUser {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id; //기본키

    @Column(name = "isLikeTrue")
    private Boolean isLikeTrue; //좋아요 여부
    @Column(name = "isHostTrue")
    private Boolean isHostTrue; //개설자 여부

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "do_id")
    private Do myDo;

    @Builder
    private DoOfUser(Boolean isLikeTrue, Boolean isHostTrue, User user, Do myDo)
    {
        this.isLikeTrue = isLikeTrue;
        this.isHostTrue = isHostTrue;
        this.user = user;
        this.myDo = myDo;
    }
}
