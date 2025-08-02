package com.karthik.moneymanager.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.Builder;

@Entity
@Table(name = "tbl_profiles")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;

    @Column
    private String email;
    private String password; 
    private String profileImageUrl;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private Boolean isActive;
    private String activationToken;

    @PrePersist
    private void prePersist(){
        if(this.isActive == null) {
            this.isActive = false;
        }
    }

    public Object getRole() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRole'");
    }

}
