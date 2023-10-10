package org.datko.diplom_grekov.service;

import org.datko.diplom_grekov.entity.ObjectSurv;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface ObjectSurvService {
    Iterable<ObjectSurv> findAll();
    Optional<ObjectSurv> findById(Integer id);
    Optional<ObjectSurv> findBySurveyId(Integer surveyId);
    Optional<ObjectSurv> add(Integer surveyId, ObjectSurv objectSurv);
    Optional<ObjectSurv> deleteById(Integer id);
    Optional<ObjectSurv> upRating(Integer id, ObjectSurv objectSurv);
}
