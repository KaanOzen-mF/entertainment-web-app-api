package com.kaanozen.entertainment_app_api.service;

import com.kaanozen.entertainment_app_api.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * A service that implements Spring Security's {@link UserDetailsService} interface.
 * <p>
 * This class is responsible for loading user-specific data from the database.
 * It acts as the bridge between our application's user model (the {@code User} entity)
 * and Spring Security's internal authentication mechanism.
 */
@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public JpaUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Locates the user based on the username (in our case, the email address).
     * This method is called by Spring Security's AuthenticationManager during the
     * authentication process.
     *
     * @param email The email address identifying the user whose data is required.
     * @return A {@link UserDetails} object containing the user's information.
     * @throws UsernameNotFoundException if the user could not be found.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Use the UserRepository to find the user by their email.
        return userRepository.findByEmail(email)
                // If the user is not found, throw a UsernameNotFoundException, which is the
                // standard exception expected by Spring Security in this scenario.
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }
}
