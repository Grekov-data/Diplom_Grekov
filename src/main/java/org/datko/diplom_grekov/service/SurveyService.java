package org.datko.diplom_grekov.service;

import org.datko.diplom_grekov.entity.Survey;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface SurveyService {
    Iterable<Survey> findAll();                               //получить все опросники
    Optional<Survey> findById(Integer id);                    //получить по id
    Optional<Survey> findByCompanyId(Integer companyId);      //получить список опросов определённой компании
    Optional<Survey> add(Integer companyId, Survey survey);   //добавить новый опросник
    Optional<Survey> deleteById(Integer id);                  //удалить опросник по id
    Optional<Survey> updateById(Integer id, Survey survey);   //отредактировать опрос по id
}
