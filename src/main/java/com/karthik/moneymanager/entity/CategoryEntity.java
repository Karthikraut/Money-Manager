package com.karthik.moneymanager.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_categories")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private String type;

    private String icon;

    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(name = "profile_id", nullable = false)
    private ProfileEntity profile;
}



// The following code is part of an Entity class where we define a relationship
// between the current entity (example: OrderEntity) and ProfileEntity

// ---------------------- CODE START -------------------------

    // 🔹 Defines a Many-to-One relationship
    // This means: Many records in this table (e.g., Orders) can be linked to 
    // ONE record in ProfileEntity (e.g., one user can have many orders).

    //@ManyToOne(fetch = FetchType.LAZY)  
    // FetchType.LAZY → Data from ProfileEntity will NOT be loaded immediately
    // when you fetch this entity (Orders). It will only load profile details
    // when you explicitly call `getProfile()` in your code.
    //
    // Example:
    //  - Fetch Orders list -> Only Order data will come
    //  - When you access order.getProfile(), Hibernate will fire a separate
    //    SQL query to fetch Profile details on-demand (lazy loading).
    //
    // Benefits:
    //  ✅ Faster queries if you don't need profile details immediately.
    //  ✅ Saves memory and reduces unnecessary database joins.
    //
    // Alternative:
    //  FetchType.EAGER → Would load Profile details immediately with Orders.

    //@JoinColumn(name = "profile_id", nullable = false)  
    // @JoinColumn defines the foreign key column in this entity's table.
    // - name = "profile_id" → The column name in the Orders table that stores
    //   the Profile's primary key (id).
    // - nullable = false → This relationship is mandatory, meaning every Order
    //   must be linked to a Profile (cannot be null).

    //private ProfileEntity profile;  
    // This is the reference to the ProfileEntity object linked to this record.
    // - It's like saying: each Order belongs to ONE specific Profile (user).
    // - When you call `order.getProfile()`, you get the Profile details for that order.

// ---------------------- CODE END ---------------------------
