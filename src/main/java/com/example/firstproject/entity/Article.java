package com.example.firstproject.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

@Entity
@AllArgsConstructor     //생성자 자동 생성
@NoArgsConstructor      //디폴트 생성자 자동 생성
@ToString
@Setter
@Getter
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)     //DB가 id를 자동 생성
    private Long id;
    @Column
    private String title;
    @Column
    private String content;

}
