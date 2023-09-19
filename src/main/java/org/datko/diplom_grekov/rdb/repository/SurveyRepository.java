package org.datko.diplom_grekov.rdb.repository;

import org.datko.diplom_grekov.entity.Survey;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SurveyRepository extends CrudRepository<Survey, Integer> {
    Optional<Survey> findByName(String name);
}
