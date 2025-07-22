// src/main/java/com/example/entertainmentappapi/service/DataSeeder.java
package com.kaanozen.entertainment_app_api.service;

import com.kaanozen.entertainment_app_api.entity.MediaContent;
import com.kaanozen.entertainment_app_api.repository.MediaContentRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    private final MediaContentRepository mediaContentRepository;
    private final ObjectMapper objectMapper; // JSON işlemek için

    public DataSeeder(MediaContentRepository mediaContentRepository, ObjectMapper objectMapper) {
        this.mediaContentRepository = mediaContentRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        // Eğer veritabanında zaten veri varsa, tekrar ekleme yapma.
        if (mediaContentRepository.count() == 0) {
            // resources klasöründeki data.json dosyasını oku
            InputStream inputStream = new ClassPathResource("data.json").getInputStream();
            List<JsonNode> jsonNodes = objectMapper.readValue(inputStream, new TypeReference<>() {});

            List<MediaContent> mediaContents = new ArrayList<>();
            for (JsonNode node : jsonNodes) {
                MediaContent content = new MediaContent();
                content.setTitle(node.get("title").asText());
                content.setYear(node.get("year").asInt());
                content.setCategory(node.get("category").asText());
                content.setRating(node.get("rating").asText());
                content.setTrending(node.get("isTrending").asBoolean());

                // Thumbnail nesnelerini ayıkla
                JsonNode thumbnailNode = node.get("thumbnail");
                if (thumbnailNode.has("trending")) {
                    content.setThumbnailTrendingSmall(thumbnailNode.get("trending").get("small").asText());
                    content.setThumbnailTrendingLarge(thumbnailNode.get("trending").get("large").asText());
                }
                content.setThumbnailRegularSmall(thumbnailNode.get("regular").get("small").asText());
                content.setThumbnailRegularMedium(thumbnailNode.get("regular").get("medium").asText());
                content.setThumbnailRegularLarge(thumbnailNode.get("regular").get("large").asText());

                mediaContents.add(content);
            }

            // Tüm listeyi veritabanına tek seferde kaydet.
            mediaContentRepository.saveAll(mediaContents);
            System.out.println(mediaContents.size() + " add media contents to database.");
        } else {
            System.out.println("Data already exists in the database. Skipping seeding.");
        }
    }
}