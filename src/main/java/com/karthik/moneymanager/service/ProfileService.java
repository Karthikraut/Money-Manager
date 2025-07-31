package com.karthik.moneymanager.service;


import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.karthik.moneymanager.dto.ProfileDTO;
import com.karthik.moneymanager.entity.ProfileEntity;
import com.karthik.moneymanager.repository.ProfileRepository;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    // ✅ Constructor injection (best practice)
    public ProfileService(ProfileRepository profileRepository, EmailService emailService, PasswordEncoder passwordEncoder) {
        this.profileRepository = profileRepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    // ✅ registerUser method
    public ProfileDTO registerUser(ProfileDTO profileDTO) {
        ProfileEntity newProfile = toEntity(profileDTO);
        newProfile.setActivationToken(UUID.randomUUID().toString());
        
        newProfile = profileRepository.save(newProfile);
        //SEND ACTIVATION EMAIL
        String activationLink = "http://localhost:8080/api/v1/activate?token=" + newProfile.getActivationToken();
        String subject = "Activate your Money Manager account";
        String body = "Click the link to activate your account: " + activationLink;

        emailService.sendEmail(newProfile.getEmail(), subject, body);
        return toDTO(newProfile);
    }

    // ✅ DTO to Entity conversion
    public ProfileEntity toEntity(ProfileDTO profileDTO) {
        return ProfileEntity.builder()
                .id(profileDTO.getId())
                .fullName(profileDTO.getFullName())
                .email(profileDTO.getEmail())
                .password(passwordEncoder.encode(profileDTO.getPassword()))
                .profileImageUrl(profileDTO.getProfileImageUrl())
                .createdAt(profileDTO.getCreatedAt())
                .updatedAt(profileDTO.getUpdatedAt())
                .build();
    }

    // ✅ Entity to DTO conversion
    public ProfileDTO toDTO(ProfileEntity profileEntity) {
        return ProfileDTO.builder()
                .id(profileEntity.getId())
                .fullName(profileEntity.getFullName())
                .email(profileEntity.getEmail()) // You missed this field in your version
                .profileImageUrl(profileEntity.getProfileImageUrl())
                .createdAt(profileEntity.getCreatedAt())
                .updatedAt(profileEntity.getUpdatedAt())
                .build();
    }

    public boolean activateProfile(String activationToken) {
        return profileRepository.findByActivationToken(activationToken)
                .map(
                    profile->{
                        profile.setIsActive(true);
                        profile.setActivationToken(null); // Clear the token after activation
                        profileRepository.save(profile);
                        return true;
                    }
                )
                    .orElse(false);
    }


}
