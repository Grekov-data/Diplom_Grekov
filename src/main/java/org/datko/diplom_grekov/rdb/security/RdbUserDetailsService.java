package org.datko.diplom_grekov.rdb.security;

import lombok.RequiredArgsConstructor;
import org.datko.diplom_grekov.entity.User;
import org.datko.diplom_grekov.rdb.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class RdbUserDetailsService implements UserDetailsService {
    
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findUserByLogin(username);
        if (user.isEmpty()) {
            return null;
        }

        RdbUserDetails userDetails = new RdbUserDetails();
        userDetails.setUser(user.get());
        return userDetails;
    }
}
