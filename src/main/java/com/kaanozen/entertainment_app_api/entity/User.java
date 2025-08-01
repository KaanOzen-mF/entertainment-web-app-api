package com.kaanozen.entertainment_app_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a User in the application.
 * This class serves a dual purpose:
 * 1. It is a JPA {@link Entity} that maps to the "users" table in the database.
 * 2. It implements the Spring Security {@link UserDetails} interface to integrate
 * with the authentication and authorization mechanisms.
 */
@Data // Lombok: Generates getters, setters, toString(), equals(), and hashCode() methods.
@NoArgsConstructor // Lombok: Generates a no-argument constructor.
@AllArgsConstructor // Lombok: Generates a constructor with all fields.
@Entity
@Table(name = "users")
public class User implements UserDetails {

    /**
     * The unique identifier for the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The user's email address. Must be unique across all users.
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * The user's hashed password.
     */
    @Column(nullable = false)
    private String password;

    /**
     * A collection of TMDB IDs representing the media content bookmarked by this user.
     * {@code @ElementCollection} maps a collection of basic types (like Integer)
     * to a separate table ("bookmarks"), managed by Hibernate.
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "bookmarks", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "tmdb_id")
    private Set<Integer> bookmarkedTmdbIds = new HashSet<>();

    // --- UserDetails Interface Methods ---
    // These methods are required by Spring Security to handle user authentication and authorization.

    /**
     * Returns the authorities granted to the user. For this application, we are not
     * using role-based authorization, so we return null.
     * @return A collection of granted authorities.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    /**
     * Returns the username used to authenticate the user. In this application,
     * the email is used as the username.
     * @return The user's email.
     */
    @Override
    public String getUsername() {
        return this.email;
    }

    /**
     * Indicates whether the user's account has expired. An expired account cannot be authenticated.
     * @return {@code true} because accounts in this application do not expire.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is locked or unlocked. A locked user cannot be authenticated.
     * @return {@code true} because accounts in this application are not locked.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the user's credentials (password) has expired. Expired credentials
     * prevent authentication.
     * @return {@code true} because credentials in this application do not expire.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is enabled or disabled. A disabled user cannot be authenticated.
     * @return {@code true} because accounts in this application are enabled by default.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
