package lecture.eight.student.model;

import jakarta.persistence.*;

/**
 * Сущность студента.
 * <p>
 * Представляет запись таблицы {@code students} в базе данных.
 * Используется для отображения и управления данными студентов в приложении.
 * </p>
 */
@Entity
@Table(name = "students") // Явное указание имени таблицы
public class Student {

    /** Уникальный идентификатор студента (первичный ключ). */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // Можно использовать IDENTITY для баз данных типа MySQL
    @Column(name = "id") // Явное указание имени столбца
    private long id;

    /** Имя студента. */
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    /** Фамилия студента. */
    @Column(name = "surname", nullable = false, length = 100)
    private String surname;

    /**
     * Конструктор без аргументов.
     * <p>Используется JPA/ORM и фреймворками Spring при создании объектов через рефлексию.</p>
     */
    public Student() {
    }

    /** @return идентификатор студента */
    public long getId() {
        return id;
    }

    /** @param id идентификатор студента */
    public void setId(long id) {
        this.id = id;
    }

    /** @return имя студента */
    public String getName() {
        return name;
    }

    /** @param name имя студента */
    public void setName(String name) {
        this.name = name;
    }

    /** @return фамилия студента */
    public String getSurname() {
        return surname;
    }

    /** @param surname фамилия студента */
    public void setSurname(String surname) {
        this.surname = surname;
    }
}
