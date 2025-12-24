package com.example.newcracker.service;

import com.example.newcracker.config.properties.NaverProperties;
import com.example.newcracker.dto.news.NewsDto;
import com.example.newcracker.dto.news.NewsResponse;
import com.example.newcracker.entity.Users;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewsService {

    private final NaverProperties naverProperties;
    private final WebClient webClient;

    /**
     * 1. 특정 카테고리의 뉴스만 추출
     */
    public List<NewsDto> getNewsByCategory(String category) {
        return fetchNewsFromNaver(category, 100);
    }

    /**
     * 2. 최신순 5개 뉴스 반환
     */
    public List<NewsDto> getTop5LatestNews(String category) {
        List<NewsDto> newsList = fetchNewsFromNaver(category, 100);
        return sortByLatest(newsList).stream()
                .limit(5)
                .collect(Collectors.toList());
    }

    /**
     * 3. 카테고리별 최신순 정렬 뉴스 전체 반환
     */
    public List<NewsDto> getLatestNewsByCategory(String category) {
        List<NewsDto> newsList = fetchNewsFromNaver(category, 100);
        return sortByLatest(newsList);
    }

    /**
     * 사용자의 메인 카테고리 기반 뉴스 조회
     */
    public List<NewsDto> getNewsForUser(Users user) {
        String category = String.valueOf(user.getCategory());
        return getTop5LatestNews(category);
    }

    /**
     * 네이버 API 호출 (공통 메서드)
     */
    private List<NewsDto> fetchNewsFromNaver(String query, int display) {
        try {
            NewsResponse response = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .scheme("https")
                            .host("openapi.naver.com")
                            .path("/v1/search/news.json")
                            .queryParam("query", query)
                            .queryParam("display", display)
                            .queryParam("sort", "date")
                            .build())
                    .header("X-Naver-Client-Id", naverProperties.getClientId())
                    .header("X-Naver-Client-Secret", naverProperties.getClientSecret())
                    .retrieve()
                    .bodyToMono(NewsResponse.class)
                    .block();

            if (response != null && response.getItems() != null) {
                return response.getItems();
            }

            return Collections.emptyList();

        } catch (Exception e) {
            log.error("네이버 뉴스 API 호출 실패: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * 최신순 정렬
     */
    private List<NewsDto> sortByLatest(List<NewsDto> newsList) {
        return newsList.stream()
                .sorted(Comparator.comparing(NewsDto::getParsedDate).reversed())
                .collect(Collectors.toList());
    }
}