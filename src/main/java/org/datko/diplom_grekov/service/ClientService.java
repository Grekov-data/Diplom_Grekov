package org.datko.diplom_grekov.service;

import org.datko.diplom_grekov.entity.Client;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface ClientService {
    Iterable<Client> findAll();                              //получить всех пользователей
    Optional<Client> findById(Integer id);                   //получить по id
    Optional<Client> add(Client client);                         //добавить нового пользователя
    Optional<Client> deleteById(Integer id);                 //удалить пользователя по id
    Optional<Client> updateById(Integer id, Client client);      //отредактировать пользователя по id
}
