package com.example.newcracker.dto.news;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsDto {
    private String title;
    private String description;
    private String link;
    private String pubDate;

    // HTML 태그 제거 메서드
    public String getCleanTitle() {
        return title.replaceAll("<[^>]*>", "");
    }

    public String getCleanDescription() {
        return description.replaceAll("<[^>]*>", "");
    }

    // 날짜를 LocalDateTime으로 변환
    public LocalDateTime getParsedDate() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
            return LocalDateTime.parse(pubDate, formatter);
        } catch (Exception e) {
            return LocalDateTime.now();
        }
    }
}
