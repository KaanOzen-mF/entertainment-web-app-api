package com.kaanozen.entertainment_app_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Lombok: Getter, Setter, toString, equals, hashCode metodlarını otomatik oluşturur.
@NoArgsConstructor // Lombok: Boş constructor oluşturur.
@AllArgsConstructor // Lombok: Tüm alanları içeren constructor oluşturur.
@Entity // Bu sınıfın bir veritabanı varlığı (entity) olduğunu belirtir.
@Table(name = "media_content") // Hangi tabloya karşılık geldiğini belirtir.
public class MediaContent {

    @Id // Bu alanın Primary Key olduğunu belirtir.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID'nin otomatik artan olduğunu belirtir.
    private Long id;

    @Column(nullable = false) // Veritabanında bu sütunun boş olamayacağını belirtir.
    private String title;

    @Column(nullable = false)
    private int year;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String rating;

    // Java'da camelCase (isTrending) ile SQL'de snake_case (is_trending) arasında köprü kurar.
    @Column(name = "is_trending", nullable = false)
    private boolean isTrending;

    @Column(name = "thumbnail_trending_small")
    private String thumbnailTrendingSmall;

    @Column(name = "thumbnail_trending_large")
    private String thumbnailTrendingLarge;

    @Column(name = "thumbnail_regular_small", nullable = false)
    private String thumbnailRegularSmall;

    @Column(name = "thumbnail_regular_medium", nullable = false)
    private String thumbnailRegularMedium;

    @Column(name = "thumbnail_regular_large", nullable = false)
    private String thumbnailRegularLarge;
}