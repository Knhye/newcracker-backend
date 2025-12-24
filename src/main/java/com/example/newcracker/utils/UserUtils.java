package com.example.newcracker.utils;

import com.example.newcracker.entity.Users;
import com.example.newcracker.global.exception.NotLoggedInException;
import com.example.newcracker.global.exception.ResourceNotFoundException;
import com.example.newcracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

@RequiredArgsConstructor
public class UserUtils {
    private final UserRepository userRepository;
    public Users extractUser(){
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        if(email == null){
            throw new NotLoggedInException("로그인 해주세요.");
        }

        return userRepository.findByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new ResourceNotFoundException("유저를 찾을 수 없습니다."));
    }
}
