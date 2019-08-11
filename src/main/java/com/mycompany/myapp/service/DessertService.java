package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Dessert;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Dessert}.
 */
public interface DessertService {

    /**
     * Save a dessert.
     *
     * @param dessert the entity to save.
     * @return the persisted entity.
     */
    Dessert save(Dessert dessert);

    /**
     * Get all the desserts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Dessert> findAll(Pageable pageable);


    /**
     * Get the "id" dessert.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Dessert> findOne(Long id);

    /**
     * Delete the "id" dessert.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
