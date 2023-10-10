package org.datko.diplom_grekov.rdb;

import lombok.RequiredArgsConstructor;
import org.datko.diplom_grekov.entity.Company;
import org.datko.diplom_grekov.entity.Survey;
import org.datko.diplom_grekov.rdb.repository.SurveyRepository;
import org.datko.diplom_grekov.service.CompanyService;
import org.datko.diplom_grekov.service.SurveyService;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
@RequiredArgsConstructor
@Service
public class RdbSurveyService implements SurveyService {

    private final SurveyRepository surveyRepository;
    private final CompanyService companyService;

    @Override
    public Iterable<Survey> findAll() {
        return surveyRepository.findAll();
    }

    @Override
    public Optional<Survey> findById(Integer id) {
        return surveyRepository.findById(id);
    }

    @Override
    public Optional<Survey> findByCompanyId(Integer companyId, Survey survey) {
        return Optional.empty();
    }

    @Override
    public Optional<Survey> add(Integer companyId, Survey survey) {
        Optional<Company> company = companyService.findById(companyId);
        if (company.isEmpty() || surveyRepository.findByName(survey.getName()).isPresent()) {
            return Optional.empty();
        }
        survey.setCompany(company.get());
        survey.setIsActive(false);
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
            survey.setField(updated.get().getField());
            survey.setCompany(updated.get().getCompany());
            survey.setIsActive(updated.get().getIsActive());
            return Optional.of(surveyRepository.save(survey));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Survey> changeIsActive(Integer id, Survey survey) {
        Optional<Survey> updated = findById(id);
        if (updated.isPresent()) {
            survey.setId(id);
            survey.setField(updated.get().getField());
            survey.setName(updated.get().getName());
            survey.setDescription(updated.get().getDescription());
            survey.setCompany(updated.get().getCompany());
            if (updated.get().getIsActive() == true) {
                survey.setIsActive(false);
            } else {
                survey.setIsActive(true);
            }
            return Optional.of(surveyRepository.save(survey));
        } else {
            return Optional.empty();
        }
    }
}
