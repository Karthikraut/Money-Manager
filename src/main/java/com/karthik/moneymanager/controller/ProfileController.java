package com.karthik.moneymanager.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.karthik.moneymanager.dto.AuthDto;
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

    @GetMapping("/activate")
    public ResponseEntity<String> activateProfile(@RequestParam String token) {
        boolean isActivated = profileService.activateProfile(token);
        if (isActivated) {
            return ResponseEntity.ok("Profile activated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Activation token not found or already used.");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody AuthDto authDto) {
        try {
           if(!profileService.isAccountActive(authDto.getEmail())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Account is not active"));
            } 
            Map<String,Object> response = profileService.authenticateAndGenerateToken(authDto);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", e.getMessage()));
        }
        
    }

    @GetMapping("/test")
    public String test(){
        return "ProfileController is working!";
    }
}
