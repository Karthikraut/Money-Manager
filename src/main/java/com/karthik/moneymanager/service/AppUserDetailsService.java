package com.karthik.moneymanager.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.karthik.moneymanager.entity.ProfileEntity;
import com.karthik.moneymanager.repository.ProfileRepository;


import java.util.Collections;

@Service
public class AppUserDetailsService implements UserDetailsService {

    private final ProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;

    public AppUserDetailsService(ProfileRepository profileRepository, PasswordEncoder passwordEncoder) {
        this.profileRepository = profileRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        ProfileEntity profile = profileRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return User.builder()
                .username(profile.getEmail())
                .password(profile.getPassword()) // Make sure password is already encoded
                .authorities(Collections.emptyList()) // You can add roles/authorities here if needed
                .build();
    }

    

}
