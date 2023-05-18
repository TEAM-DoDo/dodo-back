package kr.ac.hansung.dodobackend.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Table(name = "communityOfUser")
@Entity
public class CommunityOfUser {
    @Id @GeneratedValue
    private Long id;

    @Column(name = "isLikeTrue")
    private Boolean isLikeTrue;

    @Column(name = "isHostTrue")
    private Boolean isHostTrue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_id")
    private Community community;
}
