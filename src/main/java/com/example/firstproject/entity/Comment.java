package com.example.firstproject.entity;

import com.example.firstproject.dto.CommentDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)     // 해당 댓글 엔티티 여러개가, 하나의 Artile에 연관됨
//    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "article_id")    // article_id 컬럼에 Article의 대표값을 저장
    private Article article;

    @Column
    private String nickname;

    @Column
    private String body;

    public static Comment createComment(CommentDto dto, Article article) {
        if (dto.getId() != null) {
            throw new IllegalStateException("댓글 생성 실패! 댓글의 id가 없어야합니다.");
        }
        if (dto.getArticleId() != article.getId()) {
            throw new IllegalStateException("댓글 생성 실패! 게시글의 id가 잘못되었습니다.");
        }

        return new Comment(
                dto.getId(),
                article,
                dto.getNickname(),
                dto.getBody()
        );
    }

    public void patch(CommentDto dto) {
        // 예외 발생
        if (this.id != dto.getId()) {
            throw new IllegalStateException("댓글 수정 실패! 잘못된 id가 입력되었습니다.");
        }

        // 객체를 갱신
        if (dto.getNickname() != null) {
            this.nickname = dto.getNickname();
        }

        if (dto.getBody() != null) {
            this.body = dto.getBody();
        }
    }
}
