package com.example.firstproject.dto;

import com.example.firstproject.entity.Article;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class ArticleForm {

    private Long id;
    private String title;
    private String content;

//    @AllArgsConstructor로 인해 생략 가능
//    public ArticleForm(String title, String content) {
//        this.title = title;
//        this.content = content;
//    }

    public Article toEntity() {
        return new Article(id, title, content);
    }
}
