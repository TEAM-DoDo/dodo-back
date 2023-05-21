package kr.ac.hansung.dodobackend.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"user", "community"}) //연관관계 필드가 여러개라면 중괄호 사용
@Table(name = "communityOfUser")
@Entity
public class CommunityOfUser {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id; //기본키

    @Column(name = "isLikeTrue")
    private Boolean isLikeTrue; //좋아요 여부
    @Column(name = "isHostTrue")
    private Boolean isHostTrue; //개설자 여부

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_id")
    private Community community;

    @Builder
    private CommunityOfUser(Boolean isLikeTrue, Boolean isHostTrue, User user, Community community)
    {
        this.isLikeTrue = isLikeTrue;
        this.isHostTrue = isHostTrue;
        this.user = user;
        this.community = community;
    }
}
