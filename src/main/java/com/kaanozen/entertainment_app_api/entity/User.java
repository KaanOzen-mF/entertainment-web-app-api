package com.kaanozen.entertainment_app_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection; // Import et
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails { // UserDetails arayüzünü implement

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "bookmarks", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "tmdb_id")
    private Set<Integer> bookmarkedTmdbIds = new HashSet<>();

    // --- UserDetails METOTLARI ---
    // Bu metotlar, kullanıcı rolleri ve hesap durumu gibi detayları yönetir.

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Kullanıcı rolleri (Admin, User vb.) için. Şimdilik boş
        return null;
    }

    @Override
    public String getUsername() {
        // Spring Security'nin 'username' olarak email.
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Hesap süresinin dolmadığını belirtir.
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Hesabın kilitli olmadığını belirtir.
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Kimlik bilgilerinin süresinin dolmadığını belirtir.
    }

    @Override
    public boolean isEnabled() {
        return true; // Hesabın aktif olduğunu belirtir.
    }
}