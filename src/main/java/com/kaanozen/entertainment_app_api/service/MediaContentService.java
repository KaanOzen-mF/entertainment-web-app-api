// src/main/java/com/example/entertainmentappapi/service/MediaContentService.java
package com.kaanozen.entertainment_app_api.service;

import com.kaanozen.entertainment_app_api.dto.MediaContentDTO;
import com.kaanozen.entertainment_app_api.dto.ThumbnailDTO;
import com.kaanozen.entertainment_app_api.repository.MediaContentRepository;
import com.kaanozen.entertainment_app_api.entity.MediaContent;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MediaContentService {

    private final MediaContentRepository mediaContentRepository;

    public MediaContentService(MediaContentRepository mediaContentRepository) {
        this.mediaContentRepository = mediaContentRepository;
    }

    // Metotların dönüş tipini List<MediaContentDTO> olarak değiştirdik.
    public List<MediaContentDTO> findAll(String category) {
        List<MediaContent> mediaList;
        if (category != null && !category.isEmpty()) {
            mediaList = mediaContentRepository.findByCategory(category);
        } else {
            mediaList = mediaContentRepository.findAll();
        }
        return mediaList.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public List<MediaContentDTO> findTrending() {
        return mediaContentRepository.findByIsTrending(true)
                .stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public List<MediaContentDTO> searchByTitle(String title) {
        return mediaContentRepository.findByTitleContainingIgnoreCase(title)
                .stream().map(this::convertToDto).collect(Collectors.toList());
    }

    // Bu özel metot, Entity'yi DTO'ya çevirir.
    private MediaContentDTO convertToDto(MediaContent entity) {
        Map<String, String> trendingThumbnails = entity.getThumbnailTrendingSmall() != null ?
                Map.of("small", entity.getThumbnailTrendingSmall(), "large", entity.getThumbnailTrendingLarge()) :
                null;

        Map<String, String> regularThumbnails = Map.of(
                "small", entity.getThumbnailRegularSmall(),
                "medium", entity.getThumbnailRegularMedium(),
                "large", entity.getThumbnailRegularLarge()
        );

        ThumbnailDTO thumbnailDTO = new ThumbnailDTO(trendingThumbnails, regularThumbnails);

        return new MediaContentDTO(
                entity.getId(),
                entity.getTitle(),
                entity.getYear(),
                entity.getCategory(),
                entity.getRating(),
                entity.isTrending(),
                thumbnailDTO
        );
    }
}