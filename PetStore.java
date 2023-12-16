package pet.store.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "pet_store")
public class PetStore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long petStoreId;

    private String storeName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Embedded
    private Address address;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "pet_store_customer",
            joinColumns = @JoinColumn(name = "pet_store_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id")
    )
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Customer> customers = new HashSet<>();

    @OneToMany(mappedBy = "petStore", cascade = CascadeType.ALL, orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Employee> employees = new HashSet<>();

    @Data
    @NoArgsConstructor
    public static class Address {
        private String street;
        private String city;
        private String state;

        @Column(name = "zip_code")
        private String zipCode;

        public Address(String street, String city, String state, String zipCode) {
            this.street = street;
            this.city = city;
            this.state = state;
            this.zipCode = zipCode;
        }

        public String getCity() {
            return city;
        }

        // Other getter methods for address properties
    }

    // Default constructor
    public PetStore() {

    }

    // Constructor that accepts another PetStore instance
    public PetStore(PetStore petStore) {
        this.petStoreId = petStore.getPetStoreId();
        this.storeName = petStore.getStoreName();
        this.phoneNumber = petStore.getPhoneNumber();
        this.address = petStore.getAddress();
    }

    // Method to add a customer to the PetStore
    public void addCustomer(Customer customer) {
        customers.add(customer);
        customer.getPetStores().add(this);
    }

    // Method to remove a customer from the PetStore
    public void removeCustomer(Customer customer) {
        customers.remove(customer);
        customer.getPetStores().remove(this);
    }

    // Method to add an employee to the PetStore
    public void addEmployee(Employee employee) {
        employees.add(employee);
        employee.setPetStore(this);
    }

    // Method to remove an employee from the PetStore
    public void removeEmployee(Employee employee) {
        employees.remove(employee);
        employee.setPetStore(null);
    }
}

