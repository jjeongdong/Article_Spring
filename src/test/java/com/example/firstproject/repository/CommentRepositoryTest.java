package com.example.firstproject.repository;

import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CommentRepositoryTest {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;

    @Autowired
    public CommentRepositoryTest(CommentRepository commentRepository, ArticleRepository articleRepository) {
        this.commentRepository = commentRepository;
        this.articleRepository = articleRepository;
    }

    @Test
    @DisplayName("특정 게시글의 모든 댓글 조회")
    void findByArticleId() {
        /* CASE 1 : 4번 게시글의 모든 댓글 조회 */
        {
            //입력 데이터 준비
            Long articleId = 4L;
            Article article = articleRepository.findById(articleId).orElse(null);

            // 실제 수행
            List <Comment> comments = commentRepository.findByArticleId(articleId);

            // 예상하기
            Comment a = new Comment(1L, article, "Park", "굳 윌 헌팅");
            Comment b = new Comment(2L, article, "Kim", "아이 엠 샘");
            Comment c = new Comment(3L, article, "Choi", "쇼생크의 탈출");

            List <Comment> expected = new ArrayList<>(Arrays.asList(a, b, c));
            // 검증
            assertEquals(expected.toString(), comments.toString(), "4번 글의 모든 댓글을 출력");
        }

        /* CASE 2 : 1번 게시글의 모든 댓글 조회 */
        {
            //입력 데이터 준비
            Long articleId = 1L;
            Article article = articleRepository.findById(articleId).orElse(null);

            // 실제 수행
            List <Comment> comments = commentRepository.findByArticleId(articleId);

            // 예상하기

            List<Comment> expected = new ArrayList<>(Arrays.asList());
            // 검증
            assertEquals(expected.toString(), comments.toString(), "1번 글 댓글 X");
        }

        /* CASE 3 : 9번 게시글의 모든 댓글 조회 */
        {
            //입력 데이터 준비
            Long articleId = 9L;
            Article article = articleRepository.findById(articleId).orElse(null);

            // 실제 수행
            List <Comment> comments = commentRepository.findByArticleId(articleId);

            // 예상하기

            List<Comment> expected = new ArrayList<>(Arrays.asList());
            // 검증
            assertEquals(expected.toString(), comments.toString(), "9번 글 댓글 X");
        }
    }

    @Test
    @DisplayName("특정 닉네임의 모든 댓글 조회")
    void findByNickname() {
        /* CASE 1 : "Park"의 모든 댓글 조회 */
        {
            // 입력 데이터를 준비
            String nickname = "Park";

            // 실제 수행
            List<Comment> comments = commentRepository.findByNickname(nickname);

            // 예상하기
            Comment a = new Comment(1L, new Article(4L, "당신의 인생 영화는?", "댓글 ㄱ"), "Park", "굳 윌 헌팅");
            Comment b = new Comment(4L, new Article(5L, "당신의 소울 푸드는?", "댓글 ㄱㄱ"), "Park", "치킨");
            Comment c = new Comment(7L, new Article(6L, "당신의 취미는?", "댓글 ㄱㄱㄱ"), "Park", "조깅");

            List<Comment> expected = new ArrayList<>(Arrays.asList(a, b, c));

            // 검증
            assertEquals(expected.toString(), comments.toString(), "Park의 모든 댓글을 출력");

        }
    }
}