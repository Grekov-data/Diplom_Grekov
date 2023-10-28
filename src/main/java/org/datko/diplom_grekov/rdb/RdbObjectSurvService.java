package org.datko.diplom_grekov.rdb;

import lombok.RequiredArgsConstructor;
import org.datko.diplom_grekov.entity.Client;
import org.datko.diplom_grekov.entity.ObjectSurv;
import org.datko.diplom_grekov.entity.Survey;
import org.datko.diplom_grekov.entity.User;
import org.datko.diplom_grekov.rdb.repository.ClientRepository;
import org.datko.diplom_grekov.rdb.repository.ObjectSurvRepository;
import org.datko.diplom_grekov.service.ClientService;
import org.datko.diplom_grekov.service.ObjectSurvService;
import org.datko.diplom_grekov.service.SurveyService;
import org.datko.diplom_grekov.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class RdbObjectSurvService implements ObjectSurvService {

    private final ObjectSurvRepository objectSurvRepository;
    private final ClientRepository clientRepository;
    private final SurveyService surveyService;
    private final UserService userService;
    private final ClientService clientService;

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
    public Optional<ObjectSurv> upRating(Integer id, ObjectSurv objectSurv, Client client) {

        UserDetails userDetails = (UserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        String login = userDetails.getUsername();
        Optional<User> user = userService.findByLogin(login);

        Optional<ObjectSurv> updated = findById(id);
        if (updated.isPresent()) {
            objectSurv.setId(id);
            objectSurv.setName(updated.get().getName());
            objectSurv.setSurvey(updated.get().getSurvey());
            objectSurv.setRating(updated.get().getRating() + 1);

            client.setId(user.get().getClient().getId());
            client.setName(user.get().getClient().getName());
            client.setGender(user.get().getClient().getGender());
            client.setRegistrationDate(user.get().getClient().getRegistrationDate());

            Set<Survey> completedSurveys = client.getCompletedSurveys();
            completedSurveys.add(objectSurv.getSurvey());

            client.setCompletedSurveys(completedSurveys);

            Optional.of(clientRepository.save(client));
            return Optional.of(objectSurvRepository.save(objectSurv));
        } else {
            return Optional.empty();
        }
    }
}
