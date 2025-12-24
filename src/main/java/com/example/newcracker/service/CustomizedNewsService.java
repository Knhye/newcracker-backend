package com.example.newcracker.service;

import com.example.newcracker.client.NaverNewsClient;
import com.example.newcracker.dto.news.NewsDto;
import com.example.newcracker.common.helper.UserHelper;
import com.example.newcracker.common.utils.NewsUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomizedNewsService {
    private final NaverNewsClient naverNewsClient;
    private final UserHelper userHelper;
    /**
     * 2. 최신순 5개 뉴스 반환
     */
    public List<NewsDto> getTop5LatestNews() {
        List<NewsDto> newsList = naverNewsClient.fetchNewsFromNaver(String.valueOf(userHelper.extractUser().getCategory()), 100);
        return NewsUtils.sortByLatest(newsList).stream()
                .limit(5)
                .collect(Collectors.toList());
    }

    /**
     * 사용자의 메인 카테고리 기반 뉴스 조회
     */
    public List<NewsDto> getNewsForUser() {
        return getTop5LatestNews();
    }
}
