package lecture.eight.student.service;

import lecture.eight.student.model.User;
import java.util.List;

/**
 * Интерфейс сервиса для управления сущностями {@link User}.
 * <p>
 * Определяет базовые операции по регистрации, проверке существования,
 * поиску и получению списка пользователей.
 * </p>
 */
public interface UserService {

    /**
     * Регистрирует нового пользователя в системе.
     *
     * @param user пользователь, которого необходимо зарегистрировать
     * @return {@code true}, если регистрация прошла успешно;
     *         {@code false}, если пользователь с таким email уже существует
     */
    boolean register(User user);

    /**
     * Проверяет существование пользователя по адресу электронной почты.
     *
     * @param email адрес электронной почты пользователя
     * @return {@code true}, если пользователь с данным email существует;
     *         {@code false} в противном случае
     */
    boolean existsByEmail(String email);

    /**
     * Находит пользователя по адресу электронной почты.
     *
     * @param email адрес электронной почты
     * @return найденный {@link User}, либо {@code null}, если пользователь не найден
     */
    User findByEmail(String email);

    /**
     * Возвращает список всех зарегистрированных пользователей.
     *
     * @return список сущностей {@link User}
     */
    List<User> findAll();

    /**
     * Находит пользователя по уникальному идентификатору.
     *
     * @param id идентификатор пользователя
     * @return {@link User}, если найден; иначе {@code null}
     */
    User findById(Long id);

    /**
     * Сохраняет пользователя (создаёт или обновляет существующую запись).
     *
     * @param user сущность пользователя
     * @return сохранённый экземпляр {@link User}
     */
    User save(User user);

    /**
     * Удаляет пользователя по идентификатору.
     *
     * @param id идентификатор пользователя, подлежащего удалению
     */
    void deleteById(Long id);
}
