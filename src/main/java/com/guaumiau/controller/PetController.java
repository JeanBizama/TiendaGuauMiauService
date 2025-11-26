package com.guaumiau.controller;

import com.guaumiau.model.PetEntity;
import com.guaumiau.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pets")
public class PetController {
    
    @Autowired
    private PetRepository petRepository;

    // Crear mascota
    @PostMapping
    public ResponseEntity<PetEntity> createPet(@RequestBody PetEntity pet) {
        if (pet.getName() == null || pet.getName().trim().isEmpty() ||
                pet.getType() == null || pet.getType().trim().isEmpty() ||
                pet.getUserEmail() == null || pet.getUserEmail().trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        PetEntity savedPet = petRepository.save(pet);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPet);
    }
    
    //Obtener mascotas por email de usuario
    @GetMapping("/email/{userEmail}")
    public ResponseEntity<List<PetEntity>> getPetsByUserEmail(@PathVariable String userEmail) {
        if (userEmail.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        List<PetEntity> pets = petRepository.findByUserEmail(userEmail);
        return ResponseEntity.ok(pets);
    }
    
    //Obtener mascota por ID
    @GetMapping("/id/{id}")
    public ResponseEntity<PetEntity> getPetById(@PathVariable Integer id) {
        return petRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    //Eliminar mascota
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable Integer id) {
        if (!petRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        petRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
