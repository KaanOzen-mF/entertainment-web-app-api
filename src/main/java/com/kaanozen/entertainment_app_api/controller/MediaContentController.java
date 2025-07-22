package com.kaanozen.entertainment_app_api.controller;

import com.kaanozen.entertainment_app_api.entity.MediaContent;
import com.kaanozen.entertainment_app_api.service.MediaContentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Bu sınıfın bir REST Controller olduğunu belirtir.
@RequestMapping("/api/v1/media") // Bu controller'daki tüm endpoint'lerin başına bu yol eklenecek.
@CrossOrigin(origins = "http://localhost:3000") // Frontend'den gelen isteklere izin ver.
public class MediaContentController {

    private final MediaContentService mediaContentService;

    public MediaContentController(MediaContentService mediaContentService) {
        this.mediaContentService = mediaContentService;
    }

    // GET /api/v1/media veya GET /api/v1/media?category=Movie
    @GetMapping
    public List<MediaContent> getAllMedia(@RequestParam(required = false) String category) {
        return mediaContentService.findAll(category);
    }

    // GET /api/v1/media/trending
    @GetMapping("/trending")
    public List<MediaContent> getTrendingMedia() {
        return mediaContentService.findTrending();
    }

    // GET /api/v1/media/search?title=beyond
    @GetMapping("/search")
    public List<MediaContent> searchMedia(@RequestParam String title) {
        return mediaContentService.searchByTitle(title);
    }
}