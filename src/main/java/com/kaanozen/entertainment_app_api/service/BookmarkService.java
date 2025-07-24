package com.kaanozen.entertainment_app_api.service;

import com.kaanozen.entertainment_app_api.dto.MediaContentDTO;
import com.kaanozen.entertainment_app_api.dto.ThumbnailDTO;
import com.kaanozen.entertainment_app_api.entity.MediaContent;
import com.kaanozen.entertainment_app_api.entity.User;
import com.kaanozen.entertainment_app_api.repository.MediaContentRepository;
import com.kaanozen.entertainment_app_api.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BookmarkService {

    private final UserRepository userRepository;
    private final MediaContentRepository mediaContentRepository;

    public BookmarkService(UserRepository userRepository, MediaContentRepository mediaContentRepository) {
        this.userRepository = userRepository;
        this.mediaContentRepository = mediaContentRepository;
    }

    // DÖNÜŞ TİPİNİ GÜNCELLEDİK
    public List<MediaContentDTO> getBookmarks() {
        User currentUser = getCurrentUser();
        return currentUser.getBookmarkedContent().stream()
                .map(this::convertToDto) // Her bir Entity'yi DTO'ya çeviriyoruz.
                .collect(Collectors.toList());
    }

    @Transactional
    public void addBookmark(Long mediaId) {
        User currentUser = getCurrentUser();
        MediaContent contentToAdd = mediaContentRepository.findById(mediaId)
                .orElseThrow(() -> new IllegalStateException("Content with id " + mediaId + " not found"));

        currentUser.getBookmarkedContent().add(contentToAdd);
        userRepository.save(currentUser);
    }

    @Transactional
    public void deleteBookmark(Long mediaId) {
        User currentUser = getCurrentUser();
        MediaContent contentToRemove = mediaContentRepository.findById(mediaId)
                .orElseThrow(() -> new IllegalStateException("Content with id " + mediaId + " not found"));

        currentUser.getBookmarkedContent().remove(contentToRemove);
        userRepository.save(currentUser);
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName();
        return userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new IllegalStateException("User not found"));
    }

    // MediaContentService'den kopyaladığımız yardımcı metot
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