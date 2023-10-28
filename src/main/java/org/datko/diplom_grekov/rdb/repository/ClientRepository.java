package org.datko.diplom_grekov.rdb.repository;

import org.datko.diplom_grekov.entity.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends CrudRepository<Client, Integer> {

}
