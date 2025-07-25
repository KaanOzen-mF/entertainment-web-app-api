// src/main/java/com/example/entertainmentappapi/controller/BookmarkController.java
package com.kaanozen.entertainment_app_api.controller;

import com.kaanozen.entertainment_app_api.service.BookmarkService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/bookmarks")
@CrossOrigin(origins = "http://localhost:3000")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    public BookmarkController(BookmarkService bookmarkService) {
        this.bookmarkService = bookmarkService;
    }

    // GET /api/v1/bookmarks -> Giriş yapmış kullanıcının favori ID'lerini getirir.
    @GetMapping
    public ResponseEntity<Set<Integer>> getBookmarks() {
        return ResponseEntity.ok(bookmarkService.getBookmarks());
    }

    // POST /api/v1/bookmarks/12345 -> 12345 TMDB ID'li içeriği favorilere ekler.
    @PostMapping("/{tmdbId}")
    public ResponseEntity<Void> addBookmark(@PathVariable Integer tmdbId) {
        bookmarkService.addBookmark(tmdbId);
        return ResponseEntity.ok().build();
    }

    // DELETE /api/v1/bookmarks/12345 -> 12345 TMDB ID'li içeriği favorilerden çıkarır.
    @DeleteMapping("/{tmdbId}")
    public ResponseEntity<Void> deleteBookmark(@PathVariable Integer tmdbId) {
        bookmarkService.deleteBookmark(tmdbId);
        return ResponseEntity.ok().build();
    }
}