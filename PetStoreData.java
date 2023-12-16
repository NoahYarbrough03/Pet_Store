package pet.store.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import pet.store.entity.Customer;
import pet.store.entity.Employee;
import pet.store.entity.PetStore;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class PetStoreData {

    private Long petStoreId;
    private String storeName;
    private Address address;
    private String phoneNumber;
    private Set<PetStoreCustomer> customers = new HashSet<>();
    private Set<PetStoreEmployee> employees = new HashSet<>();

    public PetStoreData(PetStore petStore) {
        this.petStoreId = petStore.getPetStoreId();
        this.storeName = petStore.getStoreName();
        this.phoneNumber = petStore.getPhoneNumber();

        if (petStore.getAddress() != null) {
            this.address = new Address(
                    petStore.getAddress().getStreet(),
                    petStore.getAddress().getCity(),
                    petStore.getAddress().getState(),
                    petStore.getAddress().getZipCode()
            );
        }

        if (petStore.getCustomers() != null) {
            this.customers = petStore.getCustomers().stream()
                    .map(PetStoreCustomer::new)
                    .collect(Collectors.toSet());
        }

        if (petStore.getEmployees() != null) {
            this.employees = petStore.getEmployees().stream()
                    .map(PetStoreEmployee::new)
                    .collect(Collectors.toSet());
        }
    }

    public Optional<Long> getPetStoreId() {
        return Optional.ofNullable(petStoreId);
    }

    public Optional<String> getStoreName() {
        return Optional.ofNullable(storeName);
    }

    public Optional<Address> getAddress() {
        return Optional.ofNullable(address);
    }

    public Optional<String> getPhoneNumber() {
        return Optional.ofNullable(phoneNumber);
    }

    public Optional<Set<PetStoreCustomer>> getCustomers() {
        return Optional.ofNullable(customers);
    }

    public Optional<Set<PetStoreEmployee>> getEmployees() {
        return Optional.ofNullable(employees);
    }

    @Data
    @NoArgsConstructor
    public static class PetStoreCustomer {
        private Long customerId;
        private String customerName;

        public PetStoreCustomer(Customer customer) {
            this.customerId = customer.getCustomerId();
            this.customerName = customer.getCustomerName();
        }
    }

    @Data
    @NoArgsConstructor
    public static class PetStoreEmployee {
        private Long employeeId;
        private String employeeName;

        public PetStoreEmployee(Employee employee) {
            this.employeeId = employee.getEmployeeId();
            this.employeeName = employee.getEmployeeName();
        }
    }

    @Data
    @NoArgsConstructor
    public static class Address {
        private String street;
        private String city;
        private String state;
        private String zipCode;

        public Address(String street, String city, String state, String zipCode) {
            this.street = street;
            this.city = city;
            this.state = state;
            this.zipCode = zipCode;
        }
    }
}
