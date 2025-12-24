package com.example.newcracker.utils;

import com.example.newcracker.config.properties.NaverProperties;
import com.example.newcracker.dto.news.NewsDto;
import com.example.newcracker.dto.news.NewsResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class NewsUtils {
    private final WebClient webClient;
    private final NaverProperties naverProperties;

    /**
     * 네이버 API 호출 (공통 메서드)
     */
    public List<NewsDto> fetchNewsFromNaver(String query, int display) {
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
    public List<NewsDto> sortByLatest(List<NewsDto> newsList) {
        return newsList.stream()
                .sorted(Comparator.comparing(NewsDto::getParsedDate).reversed())
                .collect(Collectors.toList());
    }
}
