package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Drink;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Drink}.
 */
public interface DrinkService {

    /**
     * Save a drink.
     *
     * @param drink the entity to save.
     * @return the persisted entity.
     */
    Drink save(Drink drink);

    /**
     * Get all the drinks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Drink> findAll(Pageable pageable);


    /**
     * Get the "id" drink.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Drink> findOne(Long id);

    /**
     * Delete the "id" drink.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
    /**
   * Save a area.
   *
   * @param area the entity to save
   * @return the persisted entity
   */
  Drink partialSave(Drink drink);
}
