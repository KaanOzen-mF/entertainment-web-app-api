// src/main/java/com/example/entertainmentappapi/controller/BookmarkController.java
package com.kaanozen.entertainment_app_api.controller;

import com.kaanozen.entertainment_app_api.dto.MediaContentDTO;
import com.kaanozen.entertainment_app_api.service.BookmarkService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bookmarks")
@CrossOrigin(origins = "http://localhost:3000")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    public BookmarkController(BookmarkService bookmarkService) {
        this.bookmarkService = bookmarkService;
    }

    // GET /api/v1/bookmarks -> Giriş yapmış kullanıcının favorilerini getirir.
    @GetMapping
    public ResponseEntity<List<MediaContentDTO>> getBookmarks() {
        return ResponseEntity.ok(bookmarkService.getBookmarks());
    }

    // POST /api/v1/bookmarks/15 -> 15 ID'li içeriği favorilere ekler.
    @PostMapping("/{mediaId}")
    public ResponseEntity<Void> addBookmark(@PathVariable Long mediaId) {
        bookmarkService.addBookmark(mediaId);
        return ResponseEntity.ok().build();
    }

    // DELETE /api/v1/bookmarks/15 -> 15 ID'li içeriği favorilerden çıkarır.
    @DeleteMapping("/{mediaId}")
    public ResponseEntity<Void> deleteBookmark(@PathVariable Long mediaId) {
        bookmarkService.deleteBookmark(mediaId);
        return ResponseEntity.ok().build();
    }
}