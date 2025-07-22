// src/main/java/com/example/entertainmentappapi/controller/MediaContentController.java
package com.kaanozen.entertainment_app_api.controller;

import com.kaanozen.entertainment_app_api.dto.MediaContentDTO; // import'u değiştir
import com.kaanozen.entertainment_app_api.service.MediaContentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/media")
@CrossOrigin(origins = "http://localhost:3000")
public class MediaContentController {

    private final MediaContentService mediaContentService;

    public MediaContentController(MediaContentService mediaContentService) {
        this.mediaContentService = mediaContentService;
    }

    @GetMapping
    public List<MediaContentDTO> getAllMedia(@RequestParam(required = false) String category) {
        return mediaContentService.findAll(category);
    }

    @GetMapping("/trending")
    public List<MediaContentDTO> getTrendingMedia() {
        return mediaContentService.findTrending();
    }

    @GetMapping("/search")
    public List<MediaContentDTO> searchMedia(@RequestParam String title) {
        return mediaContentService.searchByTitle(title);
    }
}