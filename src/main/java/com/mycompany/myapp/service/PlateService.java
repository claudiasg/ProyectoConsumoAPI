package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Plate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Plate}.
 */
public interface PlateService {

    /**
     * Save a plate.
     *
     * @param plate the entity to save.
     * @return the persisted entity.
     */
    Plate save(Plate plate);

    /**
     * Get all the plates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Plate> findAll(Pageable pageable);


    /**
     * Get the "id" plate.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Plate> findOne(Long id);

    /**
     * Delete the "id" plate.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
