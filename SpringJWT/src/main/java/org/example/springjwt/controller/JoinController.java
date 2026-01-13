package org.example.springjwt.controller;

import lombok.RequiredArgsConstructor;
import org.example.springjwt.dto.JoinRequestDto;
import org.example.springjwt.dto.JoinResponseDto;
import org.example.springjwt.service.JoinService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JoinController {

    private final JoinService joinService;

    @PostMapping("/join")
    @ResponseStatus(HttpStatus.CREATED)
    public JoinResponseDto join(@RequestBody JoinRequestDto request) {
        return joinService.joinProcess(request);
    }
}
