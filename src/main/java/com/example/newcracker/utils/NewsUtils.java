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




    /**
     * 최신순 정렬
     */
    public List<NewsDto> sortByLatest(List<NewsDto> newsList) {
        return newsList.stream()
                .sorted(Comparator.comparing(NewsDto::getParsedDate).reversed())
                .collect(Collectors.toList());
    }
}
