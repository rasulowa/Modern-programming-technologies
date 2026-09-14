package lecture.eight.student.service;

import lecture.eight.student.model.Student;
import lecture.eight.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Реализация интерфейса {@link StudentService}.
 * <p>
 * Предоставляет бизнес-логику для работы с сущностями {@link Student},
 * включая операции создания, получения, обновления и удаления студентов.
 * </p>
 *
 * <p>
 * Взаимодействует с уровнем доступа к данным через {@link StudentRepository}.
 * </p>
 */
@Service
public class StudentServiceImpl implements StudentService {

    /** Репозиторий для выполнения операций с таблицей студентов. */
    private final StudentRepository studentRepository;

    /**
     * Конструктор сервиса студентов.
     *
     * @param studentRepository репозиторий студентов
     */
    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    /**
     * Возвращает список всех студентов из базы данных.
     *
     * @return список сущностей {@link Student}
     */
    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    /**
     * Сохраняет нового или обновляет существующего студента.
     *
     * @param student объект {@link Student}, который необходимо сохранить
     * @return сохранённый экземпляр {@link Student}
     */
    @Override
    public Student save(Student student) {
        return studentRepository.save(student);
    }

    /**
     * Находит студента по его уникальному идентификатору.
     *
     * @param id идентификатор студента
     * @return найденный {@link Student}, либо {@code null}, если студент не найден
     */
    @Override
    public Student findById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    /**
     * Удаляет студента по его идентификатору.
     * <p>
     * Если студент с указанным ID не найден, метод просто завершает работу без ошибки.
     * </p>
     *
     * @param id идентификатор студента
     */
    @Override
    public void deleteById(Long id) {
        studentRepository.deleteById(id);
    }
}
