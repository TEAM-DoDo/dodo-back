package kr.ac.hansung.dodobackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "chat")
@Entity
public class Chat {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "category")
    private int category;
    @Column(name = "username")
    private String username;
    @Column(name = "time")
    private int time;
    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "do_name")
    private Community community;
}
