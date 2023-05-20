package kr.ac.hansung.dodobackend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Table(name = "post")
@Entity
public class Post {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "author")
    private String author;
    @Column(name = "date")
    private String date;
    @Column(name = "imagePath")
    private String imagePath;
    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "do_name")
    private Community community;
}
