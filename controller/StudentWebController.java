package lecture.eight.student.controller;

import lecture.eight.student.model.Student;
import lecture.eight.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Веб-контроллер для работы с сущностями студентов.
 * <p>
 * Обеспечивает обработку HTTP-запросов, связанных с добавлением, редактированием,
 * удалением и отображением списка студентов. Использует Thymeleaf-шаблоны
 * для отображения данных в пользовательском интерфейсе.
 * </p>
 */
@Controller
@RequestMapping("/students")
public class StudentWebController {

    /** Сервис для выполнения операций с объектами {@link Student}. */
    @Autowired
    private StudentService studentService;

    /**
     * Отображает список всех студентов и форму добавления нового студента.
     *
     * @param model объект {@link Model} для передачи данных в шаблон
     * @return имя шаблона страницы со списком студентов
     */
    @GetMapping
    public String listStudents(Model model) {
        model.addAttribute("students", studentService.findAll());
        model.addAttribute("student", new Student()); // Необходим для формы добавления
        return "students";
    }

    /**
     * Отображает отдельную форму добавления студента.
     * <p>Не используется, если форма добавления встроена в основную страницу.</p>
     *
     * @param model объект {@link Model} для передачи данных в шаблон
     * @return имя шаблона формы добавления студента
     */
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("student", new Student());
        return "student_form";
    }

    /**
     * Обрабатывает отправку формы добавления нового студента.
     *
     * @param student объект {@link Student}, связанный с полями формы
     * @return редирект на страницу со списком студентов
     */
    @PostMapping("/add")
    public String addStudent(@ModelAttribute Student student) {
        studentService.save(student);
        return "redirect:/students";
    }

    /**
     * Удаляет студента по его идентификатору.
     *
     * @param id идентификатор студента, которого нужно удалить
     * @return редирект на страницу со списком студентов
     */
    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable long id) {
        studentService.deleteById(id);
        return "redirect:/students";
    }

    /**
     * Переключает интерфейс в режим редактирования выбранного студента.
     *
     * @param id идентификатор студента для редактирования
     * @param model объект {@link Model} для передачи данных в шаблон
     * @return имя шаблона со списком студентов, где один из них отображается в режиме редактирования
     */
    @GetMapping("/edit/{id}")
    public String editStudent(@PathVariable long id, Model model) {
        model.addAttribute("students", studentService.findAll());
        model.addAttribute("student", studentService.findById(id));
        model.addAttribute("editingId", id);
        return "students";
    }

    /**
     * Обрабатывает сохранение изменений студента (создание или обновление).
     *
     * @param student объект {@link Student} с обновлёнными данными
     * @return редирект на страницу со списком студентов
     */
    @PostMapping("/save")
    public String saveStudent(@ModelAttribute Student student) {
        studentService.save(student);
        return "redirect:/students";
    }
}
