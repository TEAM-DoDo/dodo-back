package kr.ac.hansung.dodobackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User implements Serializable
{
    private static final long serialVersionUID = -567117648902464025L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

    @NotEmpty(message = "The phoneNumber must not be null")
    private String phoneNumber;

    @NotEmpty(message = "The nickname must not be null")
    private String nickname;

    @NotEmpty(message = "The birth must not be null")
    private String birth;

    @NotEmpty(message = "The gender must not be null")
    private String gender;

    @NotEmpty(message = "The address must not be null")
    private String address;
}