package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.PlateService;
import com.mycompany.myapp.domain.Plate;
import com.mycompany.myapp.repository.PlateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Plate}.
 */
@Service
@Transactional
public class PlateServiceImpl implements PlateService {

    private final Logger log = LoggerFactory.getLogger(PlateServiceImpl.class);

    private final PlateRepository plateRepository;

    public PlateServiceImpl(PlateRepository plateRepository) {
        this.plateRepository = plateRepository;
    }

    /**
     * Save a plate.
     *
     * @param plate the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Plate save(Plate plate) {
        log.debug("Request to save Plate : {}", plate);
        return plateRepository.save(plate);
    }

    /**
     * Get all the plates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Plate> findAll(Pageable pageable) {
        log.debug("Request to get all Plates");
        return plateRepository.findAll(pageable);
    }


    /**
     * Get one plate by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Plate> findOne(Long id) {
        log.debug("Request to get Plate : {}", id);
        return plateRepository.findById(id);
    }

    /**
     * Delete the plate by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Plate : {}", id);
        plateRepository.deleteById(id);
    }

    @Override
   public Plate partialSave(Plate partialPlate) {
       log.debug("Request to save Plate : {}", partialPlate);
       Optional<Plate> plate = plateRepository.findById(partialPlate.getId());
       if (partialPlate.getPlateID() == null) {
           partialPlate.setPlateID(plate.get().getPlateID());
       }
       if (partialPlate.getDescription() == null) {
           partialPlate.setDescription(plate.get().getDescription());
       }
       if (partialPlate.getPrice() == null) {
           partialPlate.setPrice(plate.get().getPrice());
       }
       if (partialPlate.getRestaurantName() == null) {
           partialPlate.setRestaurantName(plate.get().getRestaurantName());
       }

       return plateRepository.save(partialPlate);
   }

}
