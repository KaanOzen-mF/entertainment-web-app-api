package com.kaanozen.entertainment_app_api.service;

import com.kaanozen.entertainment_app_api.entity.MediaContent;
import com.kaanozen.entertainment_app_api.repository.MediaContentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MediaContentService {

    private final MediaContentRepository mediaContentRepository;

    public MediaContentService(MediaContentRepository mediaContentRepository) {
        this.mediaContentRepository = mediaContentRepository;
    }

    // Tüm içerikleri veya kategoriye göre filtrelenmiş içerikleri getirir
    public List<MediaContent> findAll(String category) {
        if (category != null && !category.isEmpty()) {
            return mediaContentRepository.findByCategory(category);
        }
        return mediaContentRepository.findAll();
    }

    // Trend olanları getirir
    public List<MediaContent> findTrending() {
        return mediaContentRepository.findByIsTrending(true);
    }

    // Başlığa göre arama yapar
    public List<MediaContent> searchByTitle(String title) {
        return mediaContentRepository.findByTitleContainingIgnoreCase(title);
    }
}