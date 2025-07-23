// src/main/java/com/example/entertainmentappapi/service/AuthService.java
package com.kaanozen.entertainment_app_api.service;

import com.kaanozen.entertainment_app_api.dto.AuthRequest;
import com.kaanozen.entertainment_app_api.entity.User;
import com.kaanozen.entertainment_app_api.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    // Gerekli olan Repository, PasswordEncoder, JwtService ve AuthenticationManager constructor injection ile alıyoruz.
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Yeni bir kullanıcı kaydı oluşturur.
     * @param request Kayıt için email ve şifre içeren DTO.
     * @return Kaydedilen User nesnesi.
     */
    public User register(AuthRequest request) {
        // 1. Bu email ile daha önce bir kullanıcı kaydolmuş mu diye kontrol et.
        if (userRepository.findByEmail(request.email()).isPresent()) {
            // Eğer varsa, hata fırlat. (Bunu daha sonra özel exception handling ile güzelleştireceğiz)
            throw new IllegalStateException("Email already in use");
        }

        // 2. Yeni bir User nesnesi oluştur.
        User newUser = new User();
        newUser.setEmail(request.email());
        // 3. Şifreyi ASLA düz metin olarak kaydetme! Her zaman hash'leyerek kaydet.
        newUser.setPassword(passwordEncoder.encode(request.password()));

        // 4. Yeni kullanıcıyı veritabanına kaydet ve geri döndür.
        return userRepository.save(newUser);
    }

    /**
     * Kullanıcıyı doğrular ve bir JWT döner.
     * @param request Login için email ve şifre içeren DTO.
     * @return Oluşturulan JWT.
     */
    public String login(AuthRequest request) {
        // 1. Spring Security'nin AuthenticationManager'ını kullanarak kullanıcıyı doğrula.
        // Bu metot, bizim JpaUserDetailsService'imizi ve PasswordEncoder'ımızı arka planda kullanır.
        // Eğer email/şifre yanlışsa, burada otomatik olarak bir exception fırlatılır.
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        // 2. Eğer doğrulama başarılıysa, kullanıcıyı veritabanından bul.
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // 3. O kullanıcı için bir JWT oluştur ve döndür.
        return jwtService.generateToken(user);
    }
}