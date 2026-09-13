package lecture.eight.student.service;

import lecture.eight.student.model.Student;
import java.util.List;

/**
 * Интерфейс сервиса для управления сущностями {@link Student}.
 * <p>
 * Определяет базовые операции для работы с данными студентов —
 * получение, сохранение, поиск и удаление записей.
 * </p>
 */
public interface StudentService {

    /**
     * Возвращает список всех студентов.
     *
     * @return список сущностей {@link Student}
     */
    List<Student> findAll();

    /**
     * Сохраняет нового или обновляет существующего студента.
     *
     * @param student объект {@link Student}, который нужно сохранить
     * @return сохранённый экземпляр {@link Student}
     */
    Student save(Student student);

    /**
     * Находит студента по его идентификатору.
     *
     * @param id идентификатор студента
     * @return найденный {@link Student}, либо {@code null}, если студент не найден
     */
    Student findById(Long id);

    /**
     * Удаляет студента по его идентификатору.
     * <p>
     * Если студент с таким ID не найден, метод не вызывает ошибок.
     * </p>
     *
     * @param id идентификатор студента
     */
    void deleteById(Long id);
}
