package org.datko.diplom_grekov.service;

import org.springframework.stereotype.Service;

// сервис работы с пользователем
@Service
public interface UserService {

    // метод регистрации пользователя
    // возвращает true если регистрация успешная, иначе false
    boolean register(String login, String password, String passwordConfirmation);
}
