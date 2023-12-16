package pet.store.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pet.store.controller.model.PetStoreData;
import pet.store.dao.CustomerDao;
import pet.store.dao.EmployeeDao;
import pet.store.dao.PetStoreDao;
import pet.store.entity.Customer;
import pet.store.entity.Employee;
import pet.store.entity.PetStore;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PetStoreService implements PetStoreServiceInterface {

    private final PetStoreDao petStoreDao;
    private final CustomerDao customerDao;
    private final EmployeeDao employeeDao;

    @Autowired
    public PetStoreService(PetStoreDao petStoreDao, CustomerDao customerDao, EmployeeDao employeeDao) {
        this.petStoreDao = petStoreDao;
        this.customerDao = customerDao;
        this.employeeDao = employeeDao;
    }

    @Override
    public PetStoreData savePetStore(PetStoreData petStoreData) {
        PetStore petStore = findOrCreatePetStore(petStoreData.getPetStoreId().orElse(null));

        // Set basic fields
        petStore.setStoreName(petStoreData.getStoreName().orElse(null));

        // Set address details
        petStoreData.getAddress().ifPresent(address -> {
            if (petStore.getAddress() == null) {
                petStore.setAddress(new PetStore.Address());
            }
            PetStore.Address petStoreAddress = petStore.getAddress();
            petStoreAddress.setCity(address.getCity());
            petStoreAddress.setStreet(address.getStreet());
            petStoreAddress.setState(address.getState());
            petStoreAddress.setZipCode(address.getZipCode());
        });

        // Set phone number
        petStore.setPhoneNumber(petStoreData.getPhoneNumber().orElse(null));

        // Set customers and employees
        copyCustomersAndEmployees(petStoreData, petStore);

        // Save the pet store
        PetStore savedPetStore = petStoreDao.save(petStore);
        return new PetStoreData(savedPetStore);
    }

    private void copyCustomersAndEmployees(PetStoreData petStoreData, PetStore petStore) {
        Set<Customer> customers = new HashSet<>();
        for (PetStoreData.PetStoreCustomer customerData : petStoreData.getCustomers().orElse(new HashSet<>())) {
            Customer customer = customerDao.findById(customerData.getCustomerId())
                    .orElseThrow(() -> new NoSuchElementException("Customer not found with ID: " + customerData.getCustomerId()));
            customers.add(customer);
        }
        petStore.setCustomers(customers);

        Set<Employee> employees = new HashSet<>();
        for (PetStoreData.PetStoreEmployee employeeData : petStoreData.getEmployees().orElse(new HashSet<>())) {
            Employee employee = employeeDao.findById(employeeData.getEmployeeId())
                    .orElseThrow(() -> new NoSuchElementException("Employee not found with ID: " + employeeData.getEmployeeId()));
            employees.add(employee);
        }
        petStore.setEmployees(employees);
    }

    @Override
    public PetStoreData findPetStoreById(Long petStoreId) {
        Optional<PetStore> petStore = petStoreDao.findById(petStoreId);
        return petStore.map(PetStoreData::new).orElse(null);
    }

    @Override
    public List<PetStoreData> findAllPets() {
        return petStoreDao.findAll().stream()
                .map(PetStoreData::new)
                .collect(Collectors.toList());
    }

    @Override
    public void deletePetStore(Long petStoreId) {
        PetStore petStore = petStoreDao.findById(petStoreId)
                .orElseThrow(() -> new NoSuchElementException("PetStore not found with ID: " + petStoreId));
        petStoreDao.deleteById(petStoreId);
    }

    private PetStore findOrCreatePetStore(Long petStoreId) {
        return petStoreId != null ? petStoreDao.findById(petStoreId)
                .orElseThrow(() -> new NoSuchElementException("PetStore not found with ID: " + petStoreId)) : new PetStore();
    }
}

