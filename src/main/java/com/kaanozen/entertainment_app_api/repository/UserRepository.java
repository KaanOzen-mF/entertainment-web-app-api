package com.kaanozen.entertainment_app_api.repository;

import com.kaanozen.entertainment_app_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
