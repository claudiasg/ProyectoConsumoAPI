package com.mycompany.myapp.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Plate.
 */
@Entity
@Table(name = "plate")
public class Plate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "plate_id", nullable = false)
    private String plateID;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Long price;

    @ManyToOne
    @JsonIgnoreProperties("plates")
    private Restaurant restaurantName;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlateID() {
        return plateID;
    }

    public Plate plateID(String plateID) {
        this.plateID = plateID;
        return this;
    }

    public void setPlateID(String plateID) {
        this.plateID = plateID;
    }

    public String getDescription() {
        return description;
    }

    public Plate description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPrice() {
        return price;
    }

    public Plate price(Long price) {
        this.price = price;
        return this;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Restaurant getRestaurantName() {
        return restaurantName;
    }

    public Plate restaurantName(Restaurant restaurant) {
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
        if (!(o instanceof Plate)) {
            return false;
        }
        return id != null && id.equals(((Plate) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Plate{" +
            "id=" + getId() +
            ", plateID='" + getPlateID() + "'" +
            ", description='" + getDescription() + "'" +
            ", price=" + getPrice() +
            "}";
    }
}
