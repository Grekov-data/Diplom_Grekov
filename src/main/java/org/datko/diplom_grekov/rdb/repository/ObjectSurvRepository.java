package org.datko.diplom_grekov.rdb.repository;

import org.datko.diplom_grekov.entity.ObjectSurv;
import org.datko.diplom_grekov.service.ObjectSurvService;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ObjectSurvRepository extends CrudRepository<ObjectSurv, Integer> {
    Optional<ObjectSurv> findByName(String name);
}
