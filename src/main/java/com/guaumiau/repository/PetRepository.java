package com.guaumiau.repository;

import com.guaumiau.model.PetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<PetEntity, Integer> {
    // Obtener mascotas por email de usuario
    List<PetEntity> findByUserEmail(String userEmail);
}
