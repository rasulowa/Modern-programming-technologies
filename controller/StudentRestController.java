package lecture.eight.student.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lecture.eight.student.model.Student;
import lecture.eight.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST-контроллер для управления студентами через API.
 * <p>
 * Предоставляет конечные точки для получения, добавления, обновления и удаления студентов.
 * Доступ к методам контролируется на основе ролей (требуется роль {@code ADMIN}).
 * </p>
 */
@RestController
@RequestMapping("/api/students")
public class StudentRestController {

    /** Сервис для работы с объектами {@link Student}. */
    @Autowired
    private StudentService studentService;

    /**
     * Возвращает список всех студентов.
     *
     * @return список студентов
     */
    @Operation(summary = "Получить всех студентов")
    @ApiResponse(responseCode = "200", description = "Список студентов успешно получен")
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<Student> getAllStudents() {
        return studentService.findAll();
    }

    /**
     * Возвращает информацию о студенте по его идентификатору.
     *
     * @param id идентификатор студента
     * @return объект {@link Student}, если найден, или статус 404, если не найден
     */
    @Operation(summary = "Получить студента по ID")
    @ApiResponse(responseCode = "200", description = "Студент найден")
    @ApiResponse(responseCode = "404", description = "Студент не найден")
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Student> getStudentById(@PathVariable long id) {
        Student student = studentService.findById(id);
        return student != null
                ? ResponseEntity.ok(student)
                : ResponseEntity.notFound().build();
    }

    /**
     * Добавляет нового студента.
     *
     * @param student объект {@link Student}, переданный в теле запроса
     * @return созданный студент с присвоенным идентификатором
     */
    @Operation(summary = "Добавить нового студента")
    @ApiResponse(responseCode = "201", description = "Студент успешно создан")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        Student saved = studentService.save(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /**
     * Обновляет данные существующего студента.
     *
     * @param id идентификатор студента
     * @param student обновлённые данные студента
     * @return обновлённый объект {@link Student} или статус 404, если студент не найден
     */
    @Operation(summary = "Обновить данные студента")
    @ApiResponse(responseCode = "200", description = "Студент успешно обновлён")
    @ApiResponse(responseCode = "404", description = "Студент не найден")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Student> updateStudent(@PathVariable long id, @RequestBody Student student) {
        Student existing = studentService.findById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        student.setId(id);
        return ResponseEntity.ok(studentService.save(student));
    }

    /**
     * Удаляет студента по идентификатору.
     *
     * @param id идентификатор студента
     * @return HTTP-ответ: 204 — если успешно удалён, 404 — если студент не найден
     */
    @Operation(summary = "Удалить студента")
    @ApiResponse(responseCode = "204", description = "Студент успешно удалён")
    @ApiResponse(responseCode = "404", description = "Студент не найден")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteStudent(@PathVariable long id) {
        Student existing = studentService.findById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        studentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
