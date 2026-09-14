package lecture.eight.student.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lecture.eight.student.model.Role;
import lecture.eight.student.model.User;
import lecture.eight.student.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST-контроллер для управления пользователями системы.
 * <p>
 * Доступен только пользователям с ролью {@code SUPER_ADMIN}.
 * Предоставляет методы для получения списка пользователей,
 * изменения ролей и удаления записей через REST API.
 * </p>
 */
@RestController
@RequestMapping("/api/users")
@PreAuthorize("hasRole('SUPER_ADMIN')")
public class UserRestController {

    /** Сервис для выполнения операций с пользователями. */
    @Autowired
    private UserService userService;

    /**
     * Возвращает список всех пользователей.
     *
     * @return список сущностей {@link User}
     */
    @Operation(summary = "Получить всех пользователей", description = "Возвращает список всех зарегистрированных пользователей")
    @ApiResponse(responseCode = "200", description = "Список пользователей успешно возвращён")
    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    /**
     * Изменяет роль указанного пользователя по его идентификатору.
     *
     * @param id идентификатор пользователя
     * @param role новая роль для назначения
     * @return HTTP-ответ с сообщением об успешном обновлении или ошибке
     */
    @Operation(summary = "Изменить роль пользователя", description = "Обновляет роль указанного пользователя")
    @ApiResponse(responseCode = "200", description = "Роль пользователя успешно обновлена")
    @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    @PutMapping("/{id}/role")
    public ResponseEntity<?> changeUserRole(@PathVariable Long id, @RequestParam Role role) {
        User user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Пользователь не найден");
        }
        user.setRole(role);
        userService.save(user);
        return ResponseEntity.ok().body("Роль пользователя обновлена");
    }

    /**
     * Удаляет пользователя по его идентификатору.
     *
     * @param id идентификатор пользователя
     * @return HTTP-ответ с сообщением о результате операции
     */
    @Operation(summary = "Удалить пользователя", description = "Удаляет пользователя с указанным идентификатором")
    @ApiResponse(responseCode = "200", description = "Пользователь успешно удалён")
    @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Пользователь не найден");
        }
        userService.deleteById(id);
        return ResponseEntity.ok().body("Пользователь удалён");
    }
}
