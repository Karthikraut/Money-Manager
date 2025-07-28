package com.karthik.moneymanager.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.karthik.moneymanager.dto.ProfileDTO;
import com.karthik.moneymanager.service.ProfileService;

@RestController
public class ProfileController {
    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
        System.out.println(">>>>> ProfileController initialized <<<<<");
    }

    @PostMapping("/register")
    public ResponseEntity<ProfileDTO> registerUser(@RequestBody ProfileDTO profileDTO) {
        ProfileDTO registeredProfile = profileService.registerUser(profileDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredProfile);
    }
}
