package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ArticleServiceTest {

    private final ArticleService articleService;

    @Autowired
    public ArticleServiceTest(ArticleService articleService) {
        this.articleService = articleService;
    }

    @Test
    void index() {
        Article a = new Article(1L, "가가가가", "1111");
        Article b = new Article(2L, "나나나나", "2222");
        Article c = new Article(3L, "다다다다", "3333");
        List<Article> expected = new ArrayList<Article>(Arrays.asList(a, b, c));

        List<Article> articles = articleService.index();

        assertEquals(expected.toString(), articles.toString());
        assertThat(articles.size()).isEqualTo(3);
    }

    @Test
    void show_성공() {
        Long id = 1L;
        Article expected = new Article(id, "가가가가", "1111");

        Article article = articleService.show(id);

        assertEquals(expected.toString(), article.toString());
    }

    @Test
    void show_실패() {
        Long id = -1L;
        Article expected = null;

        Article article = articleService.show(id);

        assertEquals(expected, article);
    }

    @Test
    @Transactional
    void create_성공() {
        String title = "라라라라";
        String content = "4444";
        ArticleForm dto = new ArticleForm(null, title, content);
        Article expected = new Article(4L, title, content);

        Article article = articleService.create(dto);

        assertEquals(expected.toString(), article.toString());
    }

    @Test
    @Transactional
    void create_실패_id가_포함된_dto_입력() {
        String title = "라라라라";
        String content = "4444";
        ArticleForm dto = new ArticleForm(5L, title, content);
        Article expected = null;

        Article article = articleService.create(dto);

        System.out.println("article = " + article);
        assertEquals(expected, article);
    }

    @Test
    @Transactional
    void update_성공_존재하는_id와_title_content가_있는_dto_입력() {
        // 예상
        Long id = 1L;
        String title = "dong";
        String content = "hoon";

        ArticleForm dto = new ArticleForm(id, title, content);
        Article expected = articleService.update(id, dto);

        // 실제
        Article article = new Article(id, title, content);

        // 비교
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    @Transactional
    void update_성공_존재하는_id와_title만_있는_dto_입력() {
        // 예상
        Long id = 1L;
        String title = "dong";
        String content = "1111";

        ArticleForm dto = new ArticleForm(id, title, null);
        Article expected = articleService.update(id, dto);

        //실제
        Article article = new Article(id, title, content);


        //비교
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    @Transactional
    void update_실패_존재하지_않는_id의_dto_입력() {
        // 예상
        Long id = -1L;
        String title = "dong";
        String content = "hoon";

        ArticleForm dto = new ArticleForm(id, title, content);
        Article expected = null;

        // 실제
        Article article = articleService.update(id, dto);

        // 비교
        assertEquals(expected, article);
    }

    @Test
    @Transactional
    void update_실패_id만_있는_dto_입력() {
        // 예상
        Long id = 1L;

        ArticleForm dto = new ArticleForm(id,null,null);
        Article expected = new Article(id, "가가가가", "1111");

        // 실제
        Article article = articleService.update(id, dto);

        // 비교
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    @Transactional
    void delete_성공_존재하는_id_입력() {
        // 예상
        Long id = 1L;

        Article expected = new Article(id, "가가가가", "1111");

        // 실제
        Article article = articleService.delete(id);

        // 비교
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    void delete_실패_존재하지_않는_id_입력() {
        // 예상
        Long id = -1L;

        Article expected = null;

        // 실제
        Article article = articleService.delete(id);

        //비교
        assertEquals(expected, article);
    }

}