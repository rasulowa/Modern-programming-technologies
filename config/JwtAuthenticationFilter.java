package lecture.eight.student.config;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import lecture.eight.student.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Фильтр, перехватывающий все входящие HTTP-запросы и выполняющий
 * аутентификацию на основе JWT-токена.
 * <p>
 * Если в заголовке {@code Authorization} присутствует корректный токен формата
 * {@code Bearer <token>}, фильтр:
 * <ul>
 *     <li>извлекает имя пользователя,</li>
 *     <li>проверяет подлинность токена,</li>
 *     <li>устанавливает объект аутентификации в {@link SecurityContextHolder}.</li>
 * </ul>
 * В противном случае запрос просто передаётся дальше по цепочке фильтров.
 * </p>
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    /** Утилита для генерации и проверки JWT-токенов. */
    @Autowired
    private JwtUtil jwtUtil;

    /** Сервис для загрузки данных пользователя по имени (email). */
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * Извлекает JWT-токен из заголовка {@code Authorization}, проверяет его валидность
     * и при успешной проверке устанавливает аутентификацию в контекст безопасности.
     * <p>
     * Если токен отсутствует или недействителен, запрос продолжает обработку без прерывания.
     * Для API-запросов при ошибке возвращается ответ с кодом {@code 401 Unauthorized} в формате JSON.
     * </p>
     *
     * @param request     HTTP-запрос клиента
     * @param response    HTTP-ответ
     * @param filterChain цепочка фильтров, обеспечивающая дальнейшую обработку запроса
     * @throws ServletException при общей ошибке фильтрации
     * @throws IOException      при ошибке ввода-вывода
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        String token = null;

        if (header != null && header.startsWith("Bearer ")) {
            token = header.substring(7);
            try {
                String username = jwtUtil.extractUsername(token);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    if (jwtUtil.validateToken(token, userDetails)) {
                        UsernamePasswordAuthenticationToken authToken =
                                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }

            } catch (Exception ex) {
                // Возврат JSON-ответа при ошибке токена для REST API
                if (request.getRequestURI().startsWith("/api/")) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType("application/json");
                    response.getWriter().write("{\"error\": \"" + ex.getClass().getSimpleName() + ": " + ex.getMessage() + "\"}");
                    return;
                } else {
                    throw ex;
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
