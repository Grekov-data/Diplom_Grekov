package org.datko.diplom_grekov.service;

import org.datko.diplom_grekov.entity.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {
    Iterable<User> findAll();                              //получить всех пользователей
    Optional<User> findById(Integer id);                   //получить по id
    Optional<User> add(User user);                         //добавить нового пользователя
    Optional<User> deleteById(Integer id);                 //удалить пользователя по id
    Optional<User> updateById(Integer id, User user);      //отредактировать пользователя по id
}
