package org.datko.diplom_grekov.rdb;

import lombok.RequiredArgsConstructor;
import org.datko.diplom_grekov.entity.ObjectSurv;
import org.datko.diplom_grekov.rdb.repository.ObjectSurvRepository;
import org.datko.diplom_grekov.service.ObjectSurvService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RdbObjectSurvService implements ObjectSurvService {

    private final ObjectSurvRepository objectSurvRepository;
    @Override
    public Iterable<ObjectSurv> findAll() {
        return objectSurvRepository.findAll();
    }

    @Override
    public Optional<ObjectSurv> findById(Integer id) {
        return objectSurvRepository.findById(id);
    }

    @Override
    public Optional<ObjectSurv> add(ObjectSurv objectSurv) {
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
    public Optional<ObjectSurv> updateById(Integer id, ObjectSurv objectSurv) {
        Optional<ObjectSurv> updated = findById(id);
        if (updated.isPresent()) {
            objectSurvRepository.save(objectSurv);
        }
        return Optional.empty();
    }
}
