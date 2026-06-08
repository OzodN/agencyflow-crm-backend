package com.agencyflow.crm.user.service;

import com.agencyflow.crm.common.exception.EntityNotFoundException;
import com.agencyflow.crm.user.dto.UserResponse;
import com.agencyflow.crm.user.mapper.UserMapper;
import com.agencyflow.crm.user.model.User;
import com.agencyflow.crm.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponse getById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("User with id %d not found".formatted(id))
                );

        return userMapper.toResponse(user);
    }

    @Override
    public List<UserResponse> getAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toResponse)
                .toList();
    }
}
