// src/main/java/com/example/entertainmentappapi/dto/MediaContentDTO.java
package com.kaanozen.entertainment_app_api.dto;

public record MediaContentDTO(
        Long id,
        String title,
        int year,
        String category,
        String rating,
        boolean isTrending,
        ThumbnailDTO thumbnail
) {
}