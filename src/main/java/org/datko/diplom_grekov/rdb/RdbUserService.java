package org.datko.diplom_grekov.rdb;

import lombok.RequiredArgsConstructor;
import org.datko.diplom_grekov.entity.User;
import org.datko.diplom_grekov.rdb.repository.UserRepository;
import org.datko.diplom_grekov.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RdbUserService implements UserService {

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
        user.setRegistrationDate(new Date());
        /*user.setGender(true);*/
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

    public Optional<User> updateById(Integer id, User user) {
        Optional<User> updated = findById(id);
        Optional<User> duplicatedByEmailUser = userRepository.findByEmail(user.getEmail());
        if (updated.isPresent() &&
                (duplicatedByEmailUser.isEmpty() ||
                        Objects.equals(duplicatedByEmailUser.get().getId(), id))) {
            user.setId(id);
            user.setRegistrationDate(updated.get().getRegistrationDate());
            return Optional.of(userRepository.save(user));
        } else {
            return Optional.empty();
        }
    }
}
