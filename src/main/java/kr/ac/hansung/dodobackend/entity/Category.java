package kr.ac.hansung.dodobackend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Table(name="category")
@Entity
public class Category {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "iconPath")
    private String iconPath;

    @Column(name = "trend")
    private String trendCategory;
}
