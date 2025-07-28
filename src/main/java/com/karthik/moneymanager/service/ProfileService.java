package com.karthik.moneymanager.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.karthik.moneymanager.dto.ProfileDTO;
import com.karthik.moneymanager.entity.ProfileEntity;
import com.karthik.moneymanager.repository.ProfileRepository;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;

    // ✅ Constructor injection (best practice)
    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    // ✅ registerUser method
    public ProfileDTO registerUser(ProfileDTO profileDTO) {
        ProfileEntity newProfile = toEntity(profileDTO);
        newProfile.setActivationToken(UUID.randomUUID().toString());
        newProfile = profileRepository.save(newProfile);
        return toDTO(newProfile);
    }

    // ✅ DTO to Entity conversion
    public ProfileEntity toEntity(ProfileDTO profileDTO) {
        return ProfileEntity.builder()
                .id(profileDTO.getId())
                .fullName(profileDTO.getFullName())
                .email(profileDTO.getEmail())
                .password(profileDTO.getPassword())
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
}
