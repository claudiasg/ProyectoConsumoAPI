package com.mycompany.myapp.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Restaurant.
 */
@Entity
@Table(name = "restaurant")
public class Restaurant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The firstname attribute.
     */
    @NotNull
    @ApiModelProperty(value = "The firstname attribute.", required = true)
    @Column(name = "restaurant_id", nullable = false)
    private String restaurantID;

    @Column(name = "restaurant_name")
    private String restaurantName;

    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "restaurantName")
    private Set<Plate> plates = new HashSet<>();

    @OneToMany(mappedBy = "restaurantName")
    private Set<Drink> drinks = new HashSet<>();

    @OneToMany(mappedBy = "restaurantName")
    private Set<Dessert> desserts = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("restaurants")
    private Department departmentName;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRestaurantID() {
        return restaurantID;
    }

    public Restaurant restaurantID(String restaurantID) {
        this.restaurantID = restaurantID;
        return this;
    }

    public void setRestaurantID(String restaurantID) {
        this.restaurantID = restaurantID;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public Restaurant restaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
        return this;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getAddress() {
        return address;
    }

    public Restaurant address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<Plate> getPlates() {
        return plates;
    }

    public Restaurant plates(Set<Plate> plates) {
        this.plates = plates;
        return this;
    }

    public Restaurant addPlate(Plate plate) {
        this.plates.add(plate);
        plate.setRestaurantName(this);
        return this;
    }

    public Restaurant removePlate(Plate plate) {
        this.plates.remove(plate);
        plate.setRestaurantName(null);
        return this;
    }

    public void setPlates(Set<Plate> plates) {
        this.plates = plates;
    }

    public Set<Drink> getDrinks() {
        return drinks;
    }

    public Restaurant drinks(Set<Drink> drinks) {
        this.drinks = drinks;
        return this;
    }

    public Restaurant addDrink(Drink drink) {
        this.drinks.add(drink);
        drink.setRestaurantName(this);
        return this;
    }

    public Restaurant removeDrink(Drink drink) {
        this.drinks.remove(drink);
        drink.setRestaurantName(null);
        return this;
    }

    public void setDrinks(Set<Drink> drinks) {
        this.drinks = drinks;
    }

    public Set<Dessert> getDesserts() {
        return desserts;
    }

    public Restaurant desserts(Set<Dessert> desserts) {
        this.desserts = desserts;
        return this;
    }

    public Restaurant addDessert(Dessert dessert) {
        this.desserts.add(dessert);
        dessert.setRestaurantName(this);
        return this;
    }

    public Restaurant removeDessert(Dessert dessert) {
        this.desserts.remove(dessert);
        dessert.setRestaurantName(null);
        return this;
    }

    public void setDesserts(Set<Dessert> desserts) {
        this.desserts = desserts;
    }

    public Department getDepartmentName() {
        return departmentName;
    }

    public Restaurant departmentName(Department department) {
        this.departmentName = department;
        return this;
    }

    public void setDepartmentName(Department department) {
        this.departmentName = department;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Restaurant)) {
            return false;
        }
        return id != null && id.equals(((Restaurant) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
            "id=" + getId() +
            ", restaurantID='" + getRestaurantID() + "'" +
            ", restaurantName='" + getRestaurantName() + "'" +
            ", address='" + getAddress() + "'" +
            "}";
    }
}
