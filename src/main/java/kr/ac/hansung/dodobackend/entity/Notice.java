package kr.ac.hansung.dodobackend.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = "community")
@Table(name = "notice")
@Entity
public class Notice { //커뮤니티의 공지사항
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private Long id; //기본키

    @Column(name="title")
    private String title; //제목
    @Column(name="writtenBy")
    private String writtenBy; //작성자
    @Column(name = "content")
    private String content; //내용
    @Column(name = "reportingDate")
    private String reportingDate; //작성일

    //이 공지사항이 소속된 커뮤니티의 기본키를 외래키로 받음
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="community_id")
    private Community community;

    @Builder
    private Notice(String title, String writtenBy, String content, String reportingDate, Community community)
    {
        this.title = title;
        this.writtenBy = writtenBy;
        this.content = content;
        this.reportingDate = reportingDate;
        this.community = community;
    }
}
