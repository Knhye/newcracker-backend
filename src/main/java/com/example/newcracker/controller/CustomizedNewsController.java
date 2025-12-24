package com.example.newcracker.controller;

import com.example.newcracker.dto.news.NewsDto;
import com.example.newcracker.service.CustomizedNewsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/news/user")
@RequiredArgsConstructor
@Tag(name = "Customized User Controller", description = "customized News by user Controller API")
public class CustomizedNewsController {
    private final CustomizedNewsService customizedNewsService;

    /**
     * 1. 사용자 지정 카테고리별 최신 5개 뉴스 조회
     */
    @GetMapping("/top5")
    @Operation(
            summary = "사용자 지정 카테고리별 최신 뉴스 5개 조회",
            description = "특정 카테고리의 뉴스를 조회합니다.",
            security = @SecurityRequirement(name = "accessToken")
    )
    public ResponseEntity<List<NewsDto>> getTop5News() {
        List<NewsDto> news = customizedNewsService.getTop5LatestNews();
        return ResponseEntity.ok(news);
    }

    /**
     * 2. 사용자 맞춤 뉴스 (Users 객체 기반)
     */
    @GetMapping
    @Operation(
            summary = "카테고리별 뉴스 조회",
            description = "특정 카테고리의 뉴스를 조회합니다.",
            security = @SecurityRequirement(name = "accessToken")
    )
    public ResponseEntity<List<NewsDto>> getUserNews() {
         List<NewsDto> news = customizedNewsService.getNewsForUser();
         return ResponseEntity.ok(news);
    }
}
