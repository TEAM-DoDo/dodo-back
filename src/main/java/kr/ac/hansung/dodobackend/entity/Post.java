package kr.ac.hansung.dodobackend.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = "community")
@Table(name = "post")
@Entity
public class Post {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id; //기본키

    @Column(name = "imagePath")
    private String imagePath; //이미지 경로

    //이 게시글이 소속된 커뮤니티의 기본키를 외래키로 받음
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_id")
    private Community community;

    @Builder
    public Post(String imagePath, Community community)
    {
        this.imagePath = imagePath;
        this.community = community;
    }
}
