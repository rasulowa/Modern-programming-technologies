package lecture.eight.student.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lecture.eight.student.model.User;
import lecture.eight.student.repository.UserRepository;
import lecture.eight.student.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

/**
 * REST-контроллер для операций аутентификации, таких как вход в систему.
 * <p>
 * При успешной аутентификации возвращает JWT-токен, который используется
 * для последующей авторизации пользователя при обращении к защищённым ресурсам.
 * </p>
 */
@RestController
@RequestMapping("/api/auth")
public class AuthRestController {

    /** Менеджер аутентификации Spring Security. */
    @Autowired
    private AuthenticationManager authManager;

    /** Утилита для генерации и проверки JWT-токенов. */
    @Autowired
    private JwtUtil jwtUtil;

    /** Сервис для загрузки пользовательских данных при аутентификации. */
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * Аутентифицирует пользователя и возвращает JWT-токен.
     *
     * @param request объект {@link User}, содержащий учётные данные (email и пароль)
     * @return JWT-токен при успешной аутентификации
     */
    @Operation(summary = "Вход в систему", description = "Аутентифицирует пользователя и возвращает JWT-токен")
    @ApiResponse(responseCode = "200", description = "Аутентификация успешна")
    @ApiResponse(responseCode = "401", description = "Неверные учётные данные")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User request) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(token);
    }
}
