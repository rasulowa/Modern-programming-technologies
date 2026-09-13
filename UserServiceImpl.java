package lecture.eight.student.service;

import lecture.eight.student.model.User;
import lecture.eight.student.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Реализация интерфейса {@link UserService}.
 * <p>
 * Предоставляет операции управления сущностями {@link User}:
 * регистрация, поиск, сохранение/обновление и удаление.
 * </p>
 *
 * <p>
 * Хранение данных делегируется в {@link UserRepository}, а
 * шифрование паролей выполняется через {@link PasswordEncoder}.
 * </p>
 */
@Service
public class UserServiceImpl implements UserService {

    /** Репозиторий доступа к данным пользователей. */
    private final UserRepository userRepository;

    /** Компонент для шифрования (хеширования) паролей. */
    private final PasswordEncoder passwordEncoder;

    /**
     * Конструктор сервиса пользователей.
     *
     * @param userRepository репозиторий для операций с пользователями
     * @param passwordEncoder шифратор паролей (например, BCrypt)
     */
    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Регистрирует нового пользователя.
     * <ul>
     *   <li>Проверяет непустой email;</li>
     *   <li>Проверяет, что email ещё не занят;</li>
     *   <li>Шифрует сырой пароль (если он задан);</li>
     *   <li>Сохраняет пользователя в БД.</li>
     * </ul>
     *
     * @param user пользователь для регистрации
     * @return {@code true}, если регистрация прошла успешно; {@code false}, если email уже существует или данные некорректны
     */
    @Override
    public boolean register(User user) {
        if (user == null || user.getEmail() == null) {
            return false;
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            return false;
        }
        // Шифруем сырой пароль перед сохранением
        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userRepository.save(user);
        return true;
    }

    /**
     * Проверяет существование пользователя по email.
     *
     * @param email проверяемый email
     * @return {@code true}, если пользователь с таким email существует; иначе {@code false}
     */
    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    /**
     * Ищет пользователя по email.
     *
     * @param email email пользователя
     * @return найденный {@link User} или {@code null}, если пользователь не найден
     */
    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    /**
     * Возвращает список всех пользователей.
     *
     * @return список сущностей {@link User}
     */
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * Находит пользователя по идентификатору.
     *
     * @param id идентификатор пользователя
     * @return {@link User}, если найден; иначе {@code null}
     */
    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    /**
     * Сохраняет (создаёт/обновляет) пользователя.
     * <p>
     * Если пароль не менялся (или не передан), сохраняется существующий хеш.
     * Если передан новый «сырой» пароль — он шифруется и затем сохраняется.
     * </p>
     *
     * @param user сущность пользователя для сохранения
     * @return сохранённый {@link User}
     */
    @Override
    public User save(User user) {
        if (user.getId() != null) {
            // Обновление: аккуратно обращаемся с паролем
            User current = userRepository.findById(user.getId()).orElse(null);
            if (current != null) {
                String incoming = user.getPassword();
                if (incoming == null || incoming.isBlank() || incoming.equals(current.getPassword())) {
                    // Пароль не передан или передан тот же хеш — оставляем как есть
                    user.setPassword(current.getPassword());
                } else {
                    // Похоже на новый сырой пароль — шифруем
                    user.setPassword(passwordEncoder.encode(incoming));
                }
            } else {
                // На всякий случай: если запись отсутствует, ведём себя как при создании
                if (user.getPassword() != null && !user.getPassword().isBlank()) {
                    user.setPassword(passwordEncoder.encode(user.getPassword()));
                }
            }
        } else {
            // Создание новой записи
            if (user.getPassword() != null && !user.getPassword().isBlank()) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            }
        }
        return userRepository.save(user);
    }

    /**
     * Удаляет пользователя по идентификатору.
     * <p>
     * Если записи не существует — метод завершается без ошибки.
     * </p>
     *
     * @param id идентификатор удаляемого пользователя
     */
    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
