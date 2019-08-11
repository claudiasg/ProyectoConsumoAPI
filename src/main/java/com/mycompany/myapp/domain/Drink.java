package com.mycompany.myapp.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Drink.
 */
@Entity
@Table(name = "drink")
public class Drink implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "drink_id", nullable = false)
    private String drinkID;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Long price;

    @ManyToOne
    @JsonIgnoreProperties("drinks")
    private Restaurant restaurantName;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDrinkID() {
        return drinkID;
    }

    public Drink drinkID(String drinkID) {
        this.drinkID = drinkID;
        return this;
    }

    public void setDrinkID(String drinkID) {
        this.drinkID = drinkID;
    }

    public String getDescription() {
        return description;
    }

    public Drink description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPrice() {
        return price;
    }

    public Drink price(Long price) {
        this.price = price;
        return this;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Restaurant getRestaurantName() {
        return restaurantName;
    }

    public Drink restaurantName(Restaurant restaurant) {
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
        if (!(o instanceof Drink)) {
            return false;
        }
        return id != null && id.equals(((Drink) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Drink{" +
            "id=" + getId() +
            ", drinkID='" + getDrinkID() + "'" +
            ", description='" + getDescription() + "'" +
            ", price=" + getPrice() +
            "}";
    }
}
