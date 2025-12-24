package com.example.newcracker.service;

import com.example.newcracker.dto.user.request.UserPasswordUpdateRequest;
import com.example.newcracker.dto.user.request.UserUpdateRequest;
import com.example.newcracker.dto.user.response.UpdateUserPasswordResponse;
import com.example.newcracker.dto.user.response.UserDetailResponse;
import com.example.newcracker.dto.user.response.UserUpdateResponse;
import com.example.newcracker.entity.Category;
import com.example.newcracker.entity.Users;
import com.example.newcracker.common.exception.BadRequestException;
import com.example.newcracker.common.helper.UserHelper;
import com.example.newcracker.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserHelper userHelper;

    @Transactional(readOnly = true)
    public UserDetailResponse getUserDetail() {
        Users user = userHelper.extractUser();

        return UserDetailResponse.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .createdAt(user.getCreatedAt())
                .build();
    }

    @Transactional
    public UserUpdateResponse updateUser( UserUpdateRequest request) {
        Users user = userHelper.extractUser();

        user.updateInfo(
                request.getEmail(),
                request.getNickname(),
                Category.valueOf(request.getCategory())
        );

        return UserUpdateResponse.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .category(String.valueOf(user.getCategory()))
                .createdAt(user.getCreatedAt())
                .build();
    }

    @Transactional
    public UpdateUserPasswordResponse updateUserPassword(UserPasswordUpdateRequest request) {
        Users user = userHelper.extractUser();

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new BadRequestException("현재 비밀번호가 일치하지 않습니다.");
        }

        if (passwordEncoder.matches(request.getNewPassword(), user.getPassword())) {
            throw new BadRequestException("새 비밀번호는 현재 비밀번호와 달라야 합니다.");
        }

        String newHashedPassword = passwordEncoder.encode(request.getNewPassword());

        user.updatePassword(newHashedPassword);

        return UpdateUserPasswordResponse.builder()
                .id(user.getId())
                .build();
    }
}
