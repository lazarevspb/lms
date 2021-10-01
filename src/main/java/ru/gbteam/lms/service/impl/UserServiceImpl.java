package ru.gbteam.lms.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gbteam.lms.model.User;
import ru.gbteam.lms.repository.UserRepository;
import ru.gbteam.lms.service.UserService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return Collections.emptyList() ;
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void save(User user) {

    }

    @Override
    public void delete(Long id) {

    }
}
