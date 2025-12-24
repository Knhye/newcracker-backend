package com.example.newcracker.service;

import com.example.newcracker.dto.news.NewsDto;
import com.example.newcracker.entity.Users;
import com.example.newcracker.utils.NewsUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewsService {
    private final NewsUtils newsUtils;

    /**
     * 1. 특정 카테고리의 뉴스만 추출
     */
    public List<NewsDto> getNewsByCategory(String category) {
        return newsUtils.fetchNewsFromNaver(category, 100);
    }

    /**
     * 3. 카테고리별 최신순 정렬 뉴스 전체 반환
     */
    public List<NewsDto> getLatestNewsByCategory(String category) {
        List<NewsDto> newsList = newsUtils.fetchNewsFromNaver(category, 100);
        return newsUtils.sortByLatest(newsList);
    }
}