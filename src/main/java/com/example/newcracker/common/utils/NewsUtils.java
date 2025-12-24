package com.example.newcracker.common.utils;

import com.example.newcracker.dto.news.NewsDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NewsUtils {
    /**
     * 최신순 정렬
     */
    public static List<NewsDto> sortByLatest(List<NewsDto> newsList) {
        if (newsList == null || newsList.isEmpty()) {
            return List.of();
        }

        return newsList.stream()
                .sorted(Comparator.comparing(NewsDto::getParsedDate).reversed())
                .collect(Collectors.toList());
    }
}
