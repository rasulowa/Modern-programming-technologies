package lecture.eight.student;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <h1>Веб-приложение для управления студентами и пользователями</h1>
 *
 * <p>
 * Данное приложение реализовано с использованием фреймворка <b>Spring Boot 3</b>
 * и предназначено для демонстрации архитектуры многослойного веб-приложения:
 * контроллеры (MVC), сервисный слой, уровень доступа к данным (Repository),
 * а также систему безопасности с разграничением ролей пользователей.
 * </p>
 *
 * <h2>Основные функции приложения:</h2>
 * <ul>
 *   <li>Регистрация и аутентификация пользователей через формы на основе Thymeleaf.</li>
 *   <li>Ролевая система доступа:
 *     <ul>
 *       <li><code>USER</code> — просмотр списка студентов;</li>
 *       <li><code>ADMIN</code> — добавление, редактирование и удаление студентов;</li>
 *       <li><code>SUPER_ADMIN</code> — полный доступ, включая управление пользователями.</li>
 *     </ul>
 *   </li>
 *   <li>Управление записями студентов (создание, просмотр, редактирование, удаление).</li>
 *   <li>Управление пользователями (назначение ролей, удаление пользователей).</li>
 *   <li>JWT-аутентификация для REST-запросов (эндпоинты <code>/api/**</code>).</li>
 *   <li>Swagger UI (OpenAPI 3) для документирования и тестирования REST-интерфейсов.</li>
 *   <li>Поддержка шаблонов на Thymeleaf и адаптивного интерфейса на Bootstrap 5.</li>
 *   <li>Использование встроенной базы данных H2 (в памяти) с автоматическим созданием таблиц.</li>
 * </ul>
 *
 * <h2>Основные URL-адреса приложения:</h2>
 * <ul>
 *   <li><code>/</code> — домашняя страница (может быть добавлена отдельно).</li>
 *   <li><code>/login</code> — страница входа пользователя.</li>
 *   <li><code>/register</code> — регистрация нового пользователя.</li>
 *   <li><code>/students</code> — управление студентами (для ADMIN и SUPER_ADMIN).</li>
 *   <li><code>/users</code> — панель управления пользователями (только SUPER_ADMIN).</li>
 *   <li><code>/api/students</code> — REST-эндпоинты для управления студентами.</li>
 *   <li><code>/api/users</code> — REST-эндпоинты для управления пользователями.</li>
 *   <li><code>/api/auth/login</code> — аутентификация пользователя и получение JWT-токена.</li>
 *   <li><code>/swagger-ui.html</code> — Swagger UI — документация API.</li>
 * </ul>
 *
 * <h2>Предустановленные пользователи по умолчанию:</h2>
 * <p>Создаются автоматически при запуске приложения (см. класс {@code AppInitializer}):</p>
 * <ul>
 *   <li><b>SUPER_ADMIN:</b> superadmin@example.com / password</li>
 *   <li><b>ADMIN:</b> admin@example.com / password</li>
 *   <li><b>USER:</b> user@example.com / password</li>
 * </ul>
 *
 * <h2>Технологический стек:</h2>
 * <ul>
 *   <li>Spring Boot 3 (Web, Security, Data JPA)</li>
 *   <li>Thymeleaf + Spring Security Dialect</li>
 *   <li>JWT (JSON Web Token) — для REST-аутентификации</li>
 *   <li>Swagger / OpenAPI 3 — для документации REST API</li>
 *   <li>Bootstrap 5 — оформление пользовательского интерфейса</li>
 *   <li>H2 Database — встроенная база данных в памяти</li>
 * </ul>
 *
 * <h2>Назначение проекта:</h2>
 * <p>
 * Приложение предназначено для учебных и демонстрационных целей, а также
 * для практического освоения современных технологий Spring:
 * архитектуры MVC, интеграции безопасности, REST API и шаблонов Thymeleaf.
 * </p>
 *
 * <h2>Запуск и доступ:</h2>
 * <ul>
 *   <li>Приложение запускается из класса <code>StudentApplication</code>.</li>
 *   <li>После старта доступно по адресу: <code>http://localhost:8080</code>.</li>
 *   <li>Интерфейс Swagger UI доступен по адресу: <code>http://localhost:8080/swagger-ui.html</code>.</li>
 * </ul>
 *
 * @author Автор
 * @version 1.0
 */
@SpringBootApplication
public class StudentApplication {

	/**
	 * Точка входа в приложение.
	 *
	 * @param args аргументы командной строки
	 */
	public static void main(String[] args) {
		SpringApplication.run(StudentApplication.class, args);
	}
}
