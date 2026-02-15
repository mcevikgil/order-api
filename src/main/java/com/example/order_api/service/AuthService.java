package com.example.order_api.service;

import com.example.order_api.dto.request.LoginRequest;
import com.example.order_api.dto.request.RegisterRequest;
import com.example.order_api.dto.response.LoginResponse;
import com.example.order_api.dto.response.UserResponse;
import com.example.order_api.entity.Role;
import com.example.order_api.entity.User;
import com.example.order_api.mapper.UserMapper;
import com.example.order_api.repository.UserRepository;
import com.example.order_api.security.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserMapper userMapper;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtTokenProvider jwtTokenProvider,
                       UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userMapper = userMapper;
    }

    @Transactional
    public UserResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        if (request.getPhone() != null && !request.getPhone().isEmpty()) {
            if (userRepository.existsByPhone(request.getPhone())) {
                throw new RuntimeException("Phone already exists");
            }
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setIsActive(true);
        user.setRole(Role.USER);
        User savedUser = userRepository.save(user);

        return userMapper.toResponse(savedUser);
    }

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new RuntimeException("Mail or Password wrong"));

        if (!user.getIsActive()) {
            throw new RuntimeException("User is not active");
        }

        boolean isPasswordCorrect = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!isPasswordCorrect) {
            throw new RuntimeException("Wrong password.");
        }
        
        String token = jwtTokenProvider.generateToken(user.getEmail());
        UserResponse userResponse = userMapper.toResponse(user);

        LoginResponse response = new LoginResponse();
        response.setToken(token);
        response.setType("Bearer");
        response.setUser(userResponse);

        return response;
    }
}
