package org.datko.diplom_grekov.rdb;

import lombok.RequiredArgsConstructor;
import org.datko.diplom_grekov.entity.Client;
import org.datko.diplom_grekov.entity.User;
import org.datko.diplom_grekov.rdb.repository.ClientRepository;
import org.datko.diplom_grekov.rdb.repository.UserRepository;
import org.datko.diplom_grekov.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class RdbUserService implements UserService {

    private final UserRepository userRepository;

    private final ClientRepository clientRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean register(String login, String password, String passwordConfirmation) {
        if (!password.equals(passwordConfirmation) || userRepository.findUserByLogin(login).isPresent()) {
            return false;
        }
        User user = new User();
        Client client = new Client();
        client.setName("(Имя не указано)");
        client.setGender("(не указан)");
        client.setRegistrationDate(new Date());
        clientRepository.save(client);
        user.setLogin(login);
        user.setRole("USER");
        user.setClient(client);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        return true;
    }

    @Override
    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return userRepository.findUserByLogin(login);
    }

}
