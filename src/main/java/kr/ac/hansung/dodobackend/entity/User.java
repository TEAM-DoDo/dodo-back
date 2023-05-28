package kr.ac.hansung.dodobackend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) //Entity는 기본생성자가 반드시 필요. 외부에서 생성자에 접근하여 쓸데없는 인스턴스를 만드는걸 방지하기 위해 접근자가 protected로 줌. jpa는 protected여도 접근가능
@ToString(exclude = "") //무한루프를 방지하기 위해 연관관계를 맺은 테이블은 제외
@Table(name = "user") //db에 테이블 이름을 정하기. 이 어노테이션 안쓰면 클래스 이름과 동일한 테이블명을 가짐
@Entity
public class User{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) //아이디 생성전략을 db에 의존함
    @Column(name = "id", nullable = false) //칼럼 이름 정하기, db에 들어갈 값이 null이면 안됨을 명시.
    private Long id; //기본키. 생성전략이 있으므로 이 값은 빌더 패턴에 의해 할당되지 않아야한다.

    @Column(name = "phoneNumber")
    private String phoneNumber;
    @Column(name = "nickname") //db에 칼럼 이름을 정하기. 이 어노테이션 안쓰면 필드 이름과 동일한 칼럼명을 가짐
    private String nickname;
    @Column(name = "dateOfBirth")
    private String dateOfBirth;
    @Column(name = "address")
    private String address;
    @Column(name="gender")
    private String gender;
    @Column(name="category")
    private String category;
    @Column(name="level")
    private int level;
    @Column(name="profileImagePath")
    private String profileImagePath;

    public void UpdateProfileImagePath(String newPath)
    {
        this.profileImagePath = newPath;
    }

    @Builder //id를 제외한 나머지 필드들을 매개변수로 받는 빌더패턴에 사용될 생성자
    private User(String phoneNumber, String nickname, String dateOfBirth,
                 String address, String gender, String category, int level, String profileImagePath,long id)
    {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.nickname = nickname;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.gender = gender;
        this.category = category;
        this.level = level;
        this.profileImagePath = profileImagePath;
    }
}