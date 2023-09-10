package org.datko.diplom_grekov.rdb;

import lombok.RequiredArgsConstructor;
import org.datko.diplom_grekov.entity.User;
import org.datko.diplom_grekov.rdb.repository.UserRepository;
import org.datko.diplom_grekov.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RdbUserRepository implements UserService {

    private final UserRepository userRepository;
    @Override
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> add(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return Optional.empty();
        }
        return Optional.of(userRepository.save(user));
    }

    @Override
    public Optional<User> deleteById(Integer id) {
        Optional<User> removable = findById(id);
        if (removable.isPresent()) {
            userRepository.deleteById(id);
        }
        return removable;
    }

    @Override
    public Optional<User> updateById(Integer id, User user) {
        Optional<User> updated = findById(id);
        if (updated.isPresent()) {
            user.setId(id);
            return Optional.of(userRepository.save(user));
        }
        return updated;
    }
}
