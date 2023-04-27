package kr.ac.hansung.dodobackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity

public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id")
    private int id;
    @Column(name = "do_name")
    private int doName;
    @Column(name = "chat_category")
    private int category;
    @NotEmpty(message = "Send user must not be empty.")
    private String username;
    @NotEmpty(message = "Send time must not be empty")
    private int time;
    @NotEmpty(message = "Content user must not be empty.")
    private String content;

}
