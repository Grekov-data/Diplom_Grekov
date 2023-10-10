package org.datko.diplom_grekov.rdb;

import lombok.RequiredArgsConstructor;
import org.datko.diplom_grekov.entity.Client;
import org.datko.diplom_grekov.rdb.repository.ClientRepository;
import org.datko.diplom_grekov.service.ClientService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RdbClientService implements ClientService {

    private final ClientRepository clientRepository;
    @Override
    public Iterable<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Optional<Client> findById(Integer id) {
        return clientRepository.findById(id);
    }

    @Override
    public Optional<Client> add(Client client) {
        if (clientRepository.findByEmail(client.getEmail()).isPresent()) {
            return Optional.empty();
        }
        client.setRegistrationDate(new Date());
        /*client.setGender(true);*/
        return Optional.of(clientRepository.save(client));
    }

    @Override
    public Optional<Client> deleteById(Integer id) {
        Optional<Client> removable = findById(id);
        if (removable.isPresent()) {
            clientRepository.deleteById(id);
        }
        return removable;
    }

    public Optional<Client> updateById(Integer id, Client client) {
        Optional<Client> updated = findById(id);
        Optional<Client> duplicatedByEmailClient = clientRepository.findByEmail(client.getEmail());
        if (updated.isPresent() &&
                (duplicatedByEmailClient.isEmpty() ||
                        Objects.equals(duplicatedByEmailClient.get().getId(), id))) {
            client.setId(id);
            client.setRegistrationDate(updated.get().getRegistrationDate());
            return Optional.of(clientRepository.save(client));
        } else {
            return Optional.empty();
        }
    }
}
