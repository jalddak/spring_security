package org.example.springjwt.service;

import lombok.RequiredArgsConstructor;
import org.example.springjwt.common.DuplicateException;
import org.example.springjwt.dto.JoinRequestDto;
import org.example.springjwt.dto.JoinResponseDto;
import org.example.springjwt.entity.UserEntity;
import org.example.springjwt.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JoinService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public JoinResponseDto joinProcess(JoinRequestDto request) {

        if (userRepository.existsByUsername(request.getUsername()))
            throw new DuplicateException("username '%s' already exists.".formatted(request.getUsername()));

        request.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        UserEntity save = userRepository.save(UserEntity.create(request));

        return JoinResponseDto.from(save);
    }
}
