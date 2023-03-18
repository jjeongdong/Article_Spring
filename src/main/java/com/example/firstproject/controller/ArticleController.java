package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
public class ArticleController {

    private ArticleRepository articleRepository;

    @Autowired
    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form) {
        log.info(form.toString());

        // 1. Dto를 Entity로 변환.
        Article article = form.toEntity();
        log.info(article.toString());

        // 2. Repository에게 Entity를 DB안에 저장하게 함!
        Article saved = articleRepository.save(article);
        log.info(saved.toString());
        return "redirect:/articles/" + saved.getId();
    }

    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model) {        //Parameter로 받아올때는 @PathVariable를 활용
        log.info("id = " + id);

        // 1. id로 데이터를 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);
        // 2. 가져온 데이터를 model에 등록
        model.addAttribute("article", articleEntity);
        // 3. 보여줄 페이지를 설정
        return "articles/show";
    }

    @GetMapping("/articles")
    public String index(Model model) {
        // 1. 모든 데이터를 가져오기
        List<Article> articleEntityList = (List<Article>) articleRepository.findAll();

        // 2. 가져온 데이터들을 view로 전달
        model.addAttribute("articleList", articleEntityList);

        // 3. view 페이지를 설정
        return "articles/index";
    }

    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        // 1. 수정할 데이터를 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);

        // 2. 모델에 데이터를 등록
        Model model1 = model.addAttribute("article", articleEntity);

        return "articles/edit";
    }

    @PostMapping("articles/update")
    public String update(ArticleForm form) {
        log.info(form.toString());

        // 1. DTO를 엔티티로 변화
        Article articleEntity = form.toEntity();
        log.info(articleEntity.toString());

        // 2. 엔티티를 DB에 저장
        // 2-1. DB에 있는 기존 데이터를 가져옴
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);
        // 2-2. 기존 데이터를 갱신
        if (target != null) {
            articleRepository.save(articleEntity);  //엔티티가 DB로 갱신
        }
        // 3. 수정 결과 페이지로 리다이렉트
        return "redirect:/articles/" + articleEntity.getId();
    }

    @GetMapping("articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr) {
        log.info("삭제 요청이 들어왔습니다.");

        // 1. 삭제 대상을 가져옴
        Article target = articleRepository.findById(id).orElse(null);
        log.info(target.toString());

        // 2. 대상을 삭제
        if (target != null) {
            articleRepository.delete(target);
            rttr.addFlashAttribute("msg", "삭제가 완료되었습니다.");
        }

        // 3. 결과 페이지로 리다이렉트
        return "redirect:/articles";
    }
}
