// src/main/java/com/example/entertainmentappapi/controller/BookmarkController.java
package com.kaanozen.entertainment_app_api.controller;

import com.kaanozen.entertainment_app_api.service.BookmarkService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * REST Controller for handling user bookmark operations.
 * All endpoints in this controller are protected and require JWT authentication.
 * The routes are prefixed with "/api/v1/bookmarks".
 */
@RestController
@RequestMapping("/api/v1/bookmarks")
@CrossOrigin(origins = "http://localhost:3000") // Redundant due to global CORS config, but kept for clarity.
public class BookmarkController {

    private final BookmarkService bookmarkService;

    public BookmarkController(BookmarkService bookmarkService) {
        this.bookmarkService = bookmarkService;
    }

    /**
     * Handles the request to get all bookmarks for the currently authenticated user.
     * Mapped to GET /api/v1/bookmarks
     *
     * @return A ResponseEntity containing a Set of TMDB IDs for the user's bookmarked items.
     */
    @GetMapping
    public ResponseEntity<Set<Integer>> getBookmarks() {
        return ResponseEntity.ok(bookmarkService.getBookmarks());
    }

    /**
     * Handles the request to add a new bookmark for the currently authenticated user.
     * Mapped to POST /api/v1/bookmarks/{tmdbId}
     *
     * @param tmdbId The TMDB ID of the movie or TV show to be bookmarked.
     * @return A ResponseEntity with an HTTP 200 OK status upon success.
     */
    @PostMapping("/{tmdbId}")
    public ResponseEntity<Void> addBookmark(@PathVariable Integer tmdbId) {
        bookmarkService.addBookmark(tmdbId);
        return ResponseEntity.ok().build();
    }

    /**
     * Handles the request to delete a bookmark for the currently authenticated user.
     * Mapped to DELETE /api/v1/bookmarks/{tmdbId}
     *
     * @param tmdbId The TMDB ID of the movie or TV show to be removed from bookmarks.
     * @return A ResponseEntity with an HTTP 200 OK status upon success.
     */
    @DeleteMapping("/{tmdbId}")
    public ResponseEntity<Void> deleteBookmark(@PathVariable Integer tmdbId) {
        bookmarkService.deleteBookmark(tmdbId);
        return ResponseEntity.ok().build();
    }
}
