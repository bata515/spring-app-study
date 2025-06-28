package com.example.springapp.application.users.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.springapp.domain.users.irepositoryinterface.IUserRepositoryInterface;
import com.example.springapp.infrastructer.users.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final IUserRepositoryInterface userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.example.springapp.domain.users.domainobject.User user = userRepository.findByMail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

        return User.builder()
                .username(user.getMail())
                .password(user.getPassword()) // パスワードはエンコード済みである必要があります
                .roles("USER") // 権限を設定
                .build();
    }
}

