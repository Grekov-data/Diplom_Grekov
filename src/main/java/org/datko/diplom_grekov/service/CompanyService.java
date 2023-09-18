package org.datko.diplom_grekov.service;

import org.datko.diplom_grekov.entity.Company;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface CompanyService {
    Iterable<Company> findAll();                                    //получить все компании
    Optional<Company> findById(Integer id);                         //получить по id
    Optional<Company> add(Company company);                         //добавить новую компанию
    Optional<Company> deleteById(Integer id);                       //удалить компанию по id
    Optional<Company> updateById(Integer id, Company company);      //отредактировать компанию по id
}
