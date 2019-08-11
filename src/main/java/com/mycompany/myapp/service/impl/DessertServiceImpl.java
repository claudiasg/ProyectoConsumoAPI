package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.DessertService;
import com.mycompany.myapp.domain.Dessert;
import com.mycompany.myapp.repository.DessertRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Dessert}.
 */
@Service
@Transactional
public class DessertServiceImpl implements DessertService {

    private final Logger log = LoggerFactory.getLogger(DessertServiceImpl.class);

    private final DessertRepository dessertRepository;

    public DessertServiceImpl(DessertRepository dessertRepository) {
        this.dessertRepository = dessertRepository;
    }

    /**
     * Save a dessert.
     *
     * @param dessert the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Dessert save(Dessert dessert) {
        log.debug("Request to save Dessert : {}", dessert);
        return dessertRepository.save(dessert);
    }

    /**
     * Get all the desserts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Dessert> findAll(Pageable pageable) {
        log.debug("Request to get all Desserts");
        return dessertRepository.findAll(pageable);
    }


    /**
     * Get one dessert by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Dessert> findOne(Long id) {
        log.debug("Request to get Dessert : {}", id);
        return dessertRepository.findById(id);
    }

    /**
     * Delete the dessert by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Dessert : {}", id);
        dessertRepository.deleteById(id);
    }
}
