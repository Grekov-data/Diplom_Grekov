package org.datko.diplom_grekov.service;

import org.datko.diplom_grekov.entity.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

// сервис работы с пользователем
@Service
public interface UserService {

    // метод регистрации пользователя
    // возвращает true если регистрация успешная, иначе false
    boolean register(String login, String password, String passwordConfirmation);
    Optional<User> findById(Integer id);

    Optional<User> findByLogin(String login);
}
