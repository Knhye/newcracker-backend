package com.example.newcracker.controller;

import com.example.newcracker.dto.news.NewsDto;
import com.example.newcracker.service.NewsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @Operation(
            summary = "카테고리별 뉴스 조회",
            description = "특정 카테고리의 뉴스를 조회합니다."
    )public ResponseEntity<List<NewsDto>> getNewsByCategory(@RequestParam String category) {
        List<NewsDto> news = newsService.getNewsByCategory(category);
        return ResponseEntity.ok(news);
    }



    /**
     * 3. 카테고리별 최신순 정렬 뉴스 전체 조회
     */
    @GetMapping("/category/latest")
    @Operation(
            summary = "카테고리별 뉴스 조회",
            description = "특정 카테고리의 뉴스를 조회합니다."
    )
    public ResponseEntity<List<NewsDto>> getLatestNews(
            @RequestParam String category) {
        List<NewsDto> news = newsService.getLatestNewsByCategory(category);
        return ResponseEntity.ok(news);
    }


}