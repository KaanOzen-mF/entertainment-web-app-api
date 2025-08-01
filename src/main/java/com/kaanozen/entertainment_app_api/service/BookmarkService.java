// src/main/java/com/example/entertainmentappapi/service/BookmarkService.java
package com.kaanozen.entertainment_app_api.service;

import com.kaanozen.entertainment_app_api.entity.User;
import com.kaanozen.entertainment_app_api.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class BookmarkService {

    private final UserRepository userRepository;

    public BookmarkService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Mevcut giriş yapmış kullanıcının favori TMDB ID listesini döndürür.
     */
    public Set<Integer> getBookmarks() {
        User currentUser = getCurrentUser();
        return currentUser.getBookmarkedTmdbIds();
    }

    /**
     * Mevcut giriş yapmış kullanıcının favorilerine yeni bir TMDB ID'si ekler.
     * @param tmdbId Favoriye eklenecek içeriğin TMDB ID'si.
     */
    @Transactional
    public void addBookmark(Integer tmdbId) {
        User currentUser = getCurrentUser();
        Set<Integer> bookmarks = currentUser.getBookmarkedTmdbIds();
        bookmarks.add(tmdbId);
        currentUser.setBookmarkedTmdbIds(bookmarks);
        userRepository.saveAndFlush(currentUser);
    }

    /**
     * Mevcut giriş yapmış kullanıcının favorilerinden bir TMDB ID'sini siler.
     * @param tmdbId Favoriden çıkarılacak içeriğin TMDB ID'si.
     */
    @Transactional
    public void deleteBookmark(Integer tmdbId) {
        User currentUser = getCurrentUser();
        Set<Integer> bookmarks = currentUser.getBookmarkedTmdbIds();
        bookmarks.remove(tmdbId);
        currentUser.setBookmarkedTmdbIds(bookmarks);
        userRepository.saveAndFlush(currentUser);
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName();
        return userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new IllegalStateException("User not found from security context"));
    }
}