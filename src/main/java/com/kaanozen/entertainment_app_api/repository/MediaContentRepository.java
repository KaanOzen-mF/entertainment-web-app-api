package com.kaanozen.entertainment_app_api.repository;

import com.kaanozen.entertainment_app_api.entity.MediaContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MediaContentRepository extends JpaRepository<MediaContent, Long> {
    // Kategoriye göre içerik bul (Movie veya TV Series)
    List<MediaContent> findByCategory(String category);

    // Trend olanları bul
    List<MediaContent> findByIsTrending(boolean isTrending);

    // Başlıkta arama yap (büyük/küçük harf duyarsız)
    List<MediaContent> findByTitleContainingIgnoreCase(String title);
}
