package lecture.eight.student.config;

import jakarta.annotation.PostConstruct;
import lecture.eight.student.model.*;
import lecture.eight.student.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Класс инициализации приложения, создающий базовые данные при старте.
 * <p>
 * Выполняет автоматическое заполнение базы данных начальными пользователями и студентами:
 * </p>
 * <ul>
 *     <li>Создаёт супер-администратора, администратора и обычного пользователя, если их ещё нет.</li>
 *     <li>Добавляет несколько тестовых студентов, если таблица студентов пуста.</li>
 * </ul>
 * <p>
 * Данный компонент вызывается автоматически при запуске приложения благодаря аннотации {@link PostConstruct}.
 * </p>
 */
@Component
public class AppInitializer {

    /** Репозиторий пользователей. */
    @Autowired
    private UserRepository userRepository;

    /** Репозиторий студентов. */
    @Autowired
    private StudentRepository studentRepository;

    /** Кодировщик паролей, используемый для безопасного хранения паролей. */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Метод, автоматически вызываемый после создания компонента Spring.
     * <p>
     * Проверяет наличие базовых пользователей и студентов, добавляя их при необходимости.
     * </p>
     */
    @PostConstruct
    public void init() {
        createUserIfNotExists("superadmin@example.com", "password", Role.SUPER_ADMIN);
        createUserIfNotExists("admin@example.com", "password", Role.ADMIN);
        createUserIfNotExists("user@example.com", "password", Role.USER);
        createStudentsIfEmpty();
    }

    /**
     * Создаёт пользователя с указанным email, паролем и ролью, если пользователь с таким email ещё не существует.
     * <p>
     * Пароль шифруется с использованием {@link PasswordEncoder}.
     * </p>
     *
     * @param email       адрес электронной почты пользователя
     * @param rawPassword исходный (нешифрованный) пароль
     * @param role        роль пользователя (например, {@link Role#ADMIN})
     */
    private void createUserIfNotExists(String email, String rawPassword, Role role) {
        if (!userRepository.existsByEmail(email)) {
            User user = new User();
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(rawPassword));
            user.setRole(role);
            userRepository.save(user);
            System.out.println("Создан пользователь " + role + ": " + email);
        }
    }

    /**
     * Добавляет несколько примерных записей студентов, если таблица студентов пуста.
     * <p>
     * Используется для демонстрационных целей, чтобы приложение сразу имело тестовые данные.
     * </p>
     */
    private void createStudentsIfEmpty() {
        if (studentRepository.count() == 0) {
            Student s1 = new Student();
            s1.setName("Ali");
            s1.setSurname("Hasan");

            Student s2 = new Student();
            s2.setName("Fatima");
            s2.setSurname("Kassem");

            Student s3 = new Student();
            s3.setName("Ivan");
            s3.setSurname("Petrov");

            Student s4 = new Student();
            s4.setName("Ekaterina");
            s4.setSurname("Sidorova");

            Student s5 = new Student();
            s5.setName("Dmitry");
            s5.setSurname("Alexandrov");

            studentRepository.save(s1);
            studentRepository.save(s2);
            studentRepository.save(s3);
            studentRepository.save(s4);
            studentRepository.save(s5);

            System.out.println("Созданы тестовые записи студентов.");
        }
    }

}
