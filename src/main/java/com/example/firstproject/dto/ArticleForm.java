package com.example.firstproject.dto;

import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@ToString
public class ArticleForm {

    private Long id;
    private String title;
    private String content;
    private List<Comment> comments;
//    @AllArgsConstructor로 인해 생략 가능
//    public ArticleForm(String title, String content) {
//        this.title = title;
//        this.content = content;
//    }

    public Article toEntity() {
        return new Article(id, title, content, comments);
    }
}
