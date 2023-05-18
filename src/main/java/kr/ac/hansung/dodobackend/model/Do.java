package kr.ac.hansung.dodobackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
public class Do implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "do_id")
    private int id;
    private int owner;
    private String name;
    private String category;
    @Column(name = "do_explain")
    private String explain;
    private String address;
    private String image;
}
