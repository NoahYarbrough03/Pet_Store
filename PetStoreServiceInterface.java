package pet.store.service;

import pet.store.controller.model.PetStoreData;

import java.util.List;

public interface PetStoreServiceInterface {

    PetStoreData savePetStore(PetStoreData petStoreData);

    PetStoreData findPetStoreById(Long petStoreId);

    List<PetStoreData> findAllPets();

    void deletePetStore(Long petStoreId);
}

