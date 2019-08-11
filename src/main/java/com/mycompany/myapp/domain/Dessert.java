package com.mycompany.myapp.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Dessert.
 */
@Entity
@Table(name = "dessert")
public class Dessert implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "dessert_id", nullable = false)
    private String dessertID;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Long price;

    @ManyToOne
    @JsonIgnoreProperties("desserts")
    private Restaurant restaurantName;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDessertID() {
        return dessertID;
    }

    public Dessert dessertID(String dessertID) {
        this.dessertID = dessertID;
        return this;
    }

    public void setDessertID(String dessertID) {
        this.dessertID = dessertID;
    }

    public String getDescription() {
        return description;
    }

    public Dessert description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPrice() {
        return price;
    }

    public Dessert price(Long price) {
        this.price = price;
        return this;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Restaurant getRestaurantName() {
        return restaurantName;
    }

    public Dessert restaurantName(Restaurant restaurant) {
        this.restaurantName = restaurant;
        return this;
    }

    public void setRestaurantName(Restaurant restaurant) {
        this.restaurantName = restaurant;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Dessert)) {
            return false;
        }
        return id != null && id.equals(((Dessert) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Dessert{" +
            "id=" + getId() +
            ", dessertID='" + getDessertID() + "'" +
            ", description='" + getDescription() + "'" +
            ", price=" + getPrice() +
            "}";
    }
}
