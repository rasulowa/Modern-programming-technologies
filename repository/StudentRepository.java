package lecture.eight.student.repository;

import lecture.eight.student.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий доступа к данным сущности {@link Student}.
 * <p>
 * Предоставляет базовые CRUD-операции через {@link JpaRepository}.
 * Дополнительные методы выборки можно определить через ключевые слова Spring Data JPA.
 * </p>
 */
public interface StudentRepository extends JpaRepository<Student, Long> {
    // При необходимости добавляй методы вида:
    // List<Student> findByNameContainingIgnoreCase(String name);
}
