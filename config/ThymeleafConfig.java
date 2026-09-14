package lecture.eight.student.config;

import org.thymeleaf.extras.springsecurity6.dialect.SpringSecurityDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурационный класс Thymeleaf.
 * <p>
 * Отвечает за интеграцию шаблонизатора Thymeleaf со Spring Security.
 * Добавляет поддержку security-тегов через диалект {@link SpringSecurityDialect}.
 * </p>
 */
@Configuration
public class ThymeleafConfig {

    /**
     * Регистрирует диалект {@link SpringSecurityDialect} для Thymeleaf.
     * <p>
     * Благодаря этому диалекту в шаблонах можно использовать теги,
     * связанные с безопасностью (например, {@code sec:authorize}).
     * </p>
     *
     * @return экземпляр {@link SpringSecurityDialect}
     */
    @Bean
    public SpringSecurityDialect springSecurityDialect() {
        return new SpringSecurityDialect();
    }
}
