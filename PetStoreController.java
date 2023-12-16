package pet.store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import pet.store.controller.model.PetStoreData;
import pet.store.service.PetStoreService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/pet_store")
@Slf4j
public class PetStoreController {

    private final PetStoreService petStoreService;

    @Autowired
    public PetStoreController(PetStoreService petStoreService) {
        this.petStoreService = petStoreService;
    }

    @PostMapping
    public ResponseEntity<PetStoreData> createPetStore(@RequestBody PetStoreData petStoreData) {
        log.info("Creating a Pet Store {}", petStoreData);
        PetStoreData createdPetStore = petStoreService.savePetStore(petStoreData);
        return new ResponseEntity<>(createdPetStore, HttpStatus.CREATED);
    }

    @PutMapping("/{petStoreId}")
    public ResponseEntity<PetStoreData> updatePetStore(@PathVariable Long petStoreId, @RequestBody PetStoreData petStoreData) {
        petStoreData.setPetStoreId(petStoreId);
        log.info("Updating Pet Store {}", petStoreData);
        PetStoreData updatedPetStore = petStoreService.savePetStore(petStoreData);
        return ResponseEntity.ok().body(updatedPetStore);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetStoreData> getPetStoreById(@PathVariable Long id) {
        log.info("Received request to retrieve pet store with id: {}", id);
        try {
            PetStoreData petStoreData = petStoreService.findPetStoreById(id);
            return ResponseEntity.ok().body(petStoreData);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/pets")
    public List<PetStoreData> getAllPets() {
        log.info("Received request to retrieve all pets");
        return petStoreService.findAllPets();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePetStore(@PathVariable Long id) {
        log.info("Received request to delete pet store with id: {}", id);
        petStoreService.deletePetStore(id);
        return ResponseEntity.noContent().build();
    }
}




