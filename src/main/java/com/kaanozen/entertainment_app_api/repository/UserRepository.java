package com.kaanozen.entertainment_app_api.repository;

import com.kaanozen.entertainment_app_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * A Spring Data JPA repository for the {@link User} entity.
 * <p>
 * By extending {@link JpaRepository}, we get a full set of standard CRUD (Create, Read,
 * Update, Delete) operations for the User entity without writing any implementation code.
 * Spring Data JPA provides the implementation automatically at runtime.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by their email address.
     * <p>
     * This is a "derived query method". Spring Data JPA automatically generates the
     * appropriate SQL query (e.g., "SELECT * FROM users WHERE email = ?") based on the
     * method name. The return type {@link Optional} is a best practice for handling
     * cases where a user might not be found, preventing NullPointerExceptions.
     *
     * @param email The email address to search for.
     * @return An {@link Optional} containing the found {@link User} or an empty Optional if not found.
     */
    Optional<User> findByEmail(String email);
}
