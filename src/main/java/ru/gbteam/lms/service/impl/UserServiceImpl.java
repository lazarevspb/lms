package ru.gbteam.lms.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Page<User> findPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();

        int itemCount = currentPage * pageSize;

        List<User> allUsers = findAll();
        List<User> resultListUsers;

        if (allUsers.size() < itemCount) {
            resultListUsers = Collections.emptyList();
        } else {
            int toIndex = Math.min(itemCount + pageSize, allUsers.size());
            resultListUsers = allUsers.subList(itemCount, toIndex);
        }

        return new PageImpl<>(resultListUsers, PageRequest.of(currentPage, pageSize), allUsers.size());
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
