package com.kaanozen.entertainment_app_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true) // Email'in benzersiz (unique) olmasını sağlar.
    private String email;

    @Column(nullable = false)
    private String password;

    // --- FAVORİ İLİŞKİSİ ---
    // Bir kullanıcının birden çok favori içeriği olabilir, bir içeriğin de birden çok kullanıcı tarafından favorilenebilmesi. (Many-to-Many)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "bookmarks", // İlişkinin kurulacağı ara tablonun adı
            joinColumns = @JoinColumn(name = "user_id"), // Bu tablodaki (users) foreign key
            inverseJoinColumns = @JoinColumn(name = "media_content_id") // Karşı tablodaki (media_content) foreign key
    )
    private Set<MediaContent> bookmarkedContent = new HashSet<>();
}
