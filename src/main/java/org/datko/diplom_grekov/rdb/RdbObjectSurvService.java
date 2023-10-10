package org.datko.diplom_grekov.rdb;

import lombok.RequiredArgsConstructor;
import org.datko.diplom_grekov.entity.ObjectSurv;
import org.datko.diplom_grekov.entity.Survey;
import org.datko.diplom_grekov.rdb.repository.ObjectSurvRepository;
import org.datko.diplom_grekov.service.ObjectSurvService;
import org.datko.diplom_grekov.service.SurveyService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RdbObjectSurvService implements ObjectSurvService {

    private final ObjectSurvRepository objectSurvRepository;
    private final SurveyService surveyService;
    @Override
    public Iterable<ObjectSurv> findAll() {
        return objectSurvRepository.findAll();
    }

    @Override
    public Optional<ObjectSurv> findById(Integer id) {
        return objectSurvRepository.findById(id);
    }

    @Override
    public Optional<ObjectSurv> findBySurveyId(Integer surveyId) {
        return Optional.empty();
    }

    @Override
    public Optional<ObjectSurv> add(Integer surveyId, ObjectSurv objectSurv) {
        Optional<Survey> survey = surveyService.findById(surveyId);
        if (survey.isEmpty()) {
            return Optional.empty();
        }
        objectSurv.setSurvey(survey.get());
        return Optional.of(objectSurvRepository.save(objectSurv));
    }

    @Override
    public Optional<ObjectSurv> deleteById(Integer id) {
        Optional<ObjectSurv> removable = findById(id);
        if (removable.isPresent()) {
            objectSurvRepository.deleteById(id);
        }
        return removable;
    }

    @Override
    public Optional<ObjectSurv> upRating(Integer id, ObjectSurv objectSurv) {
        Optional<ObjectSurv> updated = findById(id);
        if (updated.isPresent()) {
            objectSurv.setId(id);
            objectSurv.setName(updated.get().getName());
            objectSurv.setSurvey(updated.get().getSurvey());
            objectSurv.setRating(updated.get().getRating() + 1);
            return Optional.of(objectSurvRepository.save(objectSurv));
        } else {
            return Optional.empty();
        }
    }
}
