package com.example.newcracker.controller;

import com.example.newcracker.dto.news.NewsDto;
import com.example.newcracker.service.NewsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
@Tag(name = "News Controller", description = "News Controller API")
public class NewsController {

    private final NewsService newsService;

    /**
     * 1. 카테고리별 뉴스 조회
     */
    @GetMapping("/category")
    public ResponseEntity<List<NewsDto>> getNewsByCategory(
            @RequestParam String category) {
        List<NewsDto> news = newsService.getNewsByCategory(category);
        return ResponseEntity.ok(news);
    }

    /**
     * 2. 카테고리별 최신 5개 뉴스 조회
     */
    @GetMapping("/category/top5")
    public ResponseEntity<List<NewsDto>> getTop5News(
            @RequestParam String category) {
        List<NewsDto> news = newsService.getTop5LatestNews(category);
        return ResponseEntity.ok(news);
    }

    /**
     * 3. 카테고리별 최신순 정렬 뉴스 전체 조회
     */
    @GetMapping("/category/latest")
    public ResponseEntity<List<NewsDto>> getLatestNews(
            @RequestParam String category) {
        List<NewsDto> news = newsService.getLatestNewsByCategory(category);
        return ResponseEntity.ok(news);
    }

    /**
     * 사용자 맞춤 뉴스 (Users 객체 기반)
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NewsDto>> getUserNews(
            @PathVariable Long userId) {
        // UserService를 통해 Users 객체를 가져온다고 가정
        // Users user = userService.findById(userId);
        // List<NewsDto> news = newsService.getNewsForUser(user);
        // return ResponseEntity.ok(news);
        return ResponseEntity.ok(Collections.emptyList());
    }
}