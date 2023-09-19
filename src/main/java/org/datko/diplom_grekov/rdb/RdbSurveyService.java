package org.datko.diplom_grekov.rdb;

import lombok.RequiredArgsConstructor;
import org.datko.diplom_grekov.entity.Survey;
import org.datko.diplom_grekov.rdb.repository.SurveyRepository;
import org.datko.diplom_grekov.service.SurveyService;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
@RequiredArgsConstructor
@Service
public class RdbSurveyService implements SurveyService {

    private final SurveyRepository surveyRepository;

    @Override
    public Iterable<Survey> findAll() {
        return surveyRepository.findAll();
    }

    @Override
    public Optional<Survey> findById(Integer id) {
        return surveyRepository.findById(id);
    }

    @Override
    public Optional<Survey> add(Survey survey) {
        if (surveyRepository.findByName(survey.getName()).isPresent()) {
            return Optional.empty();
        }
        return Optional.of(surveyRepository.save(survey));
    }

    @Override
    public Optional<Survey> deleteById(Integer id) {
        Optional<Survey> removable = findById(id);
        if (removable.isPresent()) {
            surveyRepository.deleteById(id);
        }
        return removable;
    }

    @Override
    public Optional<Survey> updateById(Integer id, Survey survey) {
        Optional<Survey> updated = findById(id);
        Optional<Survey> duplicatedByNameSurvey = surveyRepository.findByName(survey.getName());
        if (updated.isPresent() &&
                (duplicatedByNameSurvey.isEmpty() ||
                        Objects.equals(duplicatedByNameSurvey.get().getId(), id))) {
            survey.setId(id);
            return Optional.of(surveyRepository.save(survey));
        } else {
            return Optional.empty();
        }
    }
}
