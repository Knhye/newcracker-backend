package com.example.newcracker.service;

import com.example.newcracker.client.NaverNewsClient;
import com.example.newcracker.dto.news.NewsDto;
import com.example.newcracker.common.utils.NewsUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewsService {
    private final NewsUtils newsUtils;
    private final NaverNewsClient naverNewsClient;

    /**
     * 1. 특정 카테고리의 뉴스만 추출
     */
    public List<NewsDto> getNewsByCategory(String category) {
        return naverNewsClient.fetchNewsFromNaver(category, 100);
    }

    /**
     * 3. 카테고리별 최신순 정렬 뉴스 전체 반환
     */
    public List<NewsDto> getLatestNewsByCategory(String category) {
        List<NewsDto> newsList = naverNewsClient.fetchNewsFromNaver(category, 100);
        return newsUtils.sortByLatest(newsList);
    }
}