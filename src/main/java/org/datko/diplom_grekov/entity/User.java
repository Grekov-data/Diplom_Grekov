package org.datko.diplom_grekov.entity;

import jakarta.persistence.*;
import lombok.Data;

// сущность "Пользователь"соответствующая таблице в БД
@Data
@Entity
@Table(name="user_t")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="login_f", nullable = false)
    private String login;

    @Column(name="password_f", nullable = false)
    private String password;    // ПАРОЛЬ В ЗАХЕШИРОВАННОМ ВИДЕ

    @Column(name="role_f", nullable = false)
    private String role;       // ДЛЯ УПРОЩЕНИЯ РОЛИ ПИШУТСЯ В СТОЛБЦЕ В ВИДЕ СТРОКИ, ОДНА РОЛЬ
}
