package kr.ac.hansung.dodobackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Table(name = "notice")
@Entity
public class Notice {
    @Id
    private String id;

    @Column(name = "text")
    private String text;
    @Column(name = "date")
    private String date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_nickname") @MapsId
    private User user;
}
