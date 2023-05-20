package kr.ac.hansung.dodobackend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Table(name= "interest")
@Entity
public class Interest {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mainCateory")
    private String MainCateoryName;

    @Column(name = "subCateory")
    private String SubCateoryName;

    @Column(name = "iconPath")
    private String iconPath;

    @OneToMany(mappedBy = "interest")
    private List<InterestOfUser> interestOfUserList = new ArrayList<>();
}
