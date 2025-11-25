package com.guaumiau.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Pet")
public class PetEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name="name_pet",nullable = false)
    private String name;
    
    @Column(name = "type_pet", nullable = false)
    private String type;
    
    @Column(name = "user_email", nullable = false)
    private String userEmail;
}
