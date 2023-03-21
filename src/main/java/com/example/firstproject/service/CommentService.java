package com.example.firstproject.service;

import com.example.firstproject.annotation.RunningTime;
import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.repository.CommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, ArticleRepository articleRepository) {
        this.commentRepository = commentRepository;
        this.articleRepository = articleRepository;
    }


    public List<CommentDto> comments(Long articleId) {
////         조회 : 해당 게시글의 댓글 목록
//        List<Comment> comments = commentRepository.findByArticleId(articleId);
//
////        변환 : 엔티티 -> DTO
//        List<CommentDto> dtos = new ArrayList<CommentDto>();
//
//        for (int i = 0; i < comments.size(); i++) {
//            Comment c = comments.get(i);
//            CommentDto dto = CommentDto.createCommentDto(c);
//            dtos.add(dto);
//        }
//
//        return dtos;

        return commentRepository.findByArticleId(articleId)
                .stream()
                .map(comment -> CommentDto.createCommentDto(comment))
                .collect(Collectors.toList());
    }

    @Transactional
    public CommentDto create(Long articleId, CommentDto dto) {

//        log.info("입력값 => {}", articleId);
//        log.info("입력값 => {}", dto);

        // 게시글 조회 및 예외 발생
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("댓글 생성 실패!"));

        // 댓글 엔티티 생성
        Comment comment = Comment.createComment(dto, article);

        // 댓글 엔티티를 DB에 저장
        Comment created = commentRepository.save(comment);

        // DTO로 변경하여 반환
        return CommentDto.createCommentDto(created);
//        CommentDto createdDto = CommentDto.createCommentDto(created);
//        log.info("반환값 => {}", createdDto);
//        return createdDto;
    }

    @Transactional
    public CommentDto update(Long id, CommentDto dto) {
        // 댓글 조회 및 예외 발생
        Comment target = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글 수정 실패! 대상 댓글이 없습니다."));

        // 댓글 수정
        target.patch(dto);

        // DB로 갱신
        Comment updated = commentRepository.save(target);

        // 댓글 엔티티를 DTO로 변환 및 반환
        return CommentDto.createCommentDto(updated);
    }

    @Transactional
    public CommentDto delete(Long id) {
        // 댓글 조회 및 예외 발생
        Comment target = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글 삭제 실패! 대상이 없습니다."));

        // 댓글 DB에서 삭제
        commentRepository.delete(target);

        // 삭제 댓글을 DTO로 반환
        return CommentDto.createCommentDto(target);
    }

    // @RunningTime 어노테이션을 CommentApiController의 delete 메소드에 붙였을 때는 로그에 찍힌 반환값이 잘 나왔는데
    // CommentService에 delete 메소드에 붙였을 때는 로그로 찍힌 반환값이 null로 나오는데 이유를 알 수 있을까요??


}
