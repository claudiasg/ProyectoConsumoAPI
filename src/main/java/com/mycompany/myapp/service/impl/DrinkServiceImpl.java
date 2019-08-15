package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.DrinkService;
import com.mycompany.myapp.domain.Drink;
import com.mycompany.myapp.repository.DrinkRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Drink}.
 */
@Service
@Transactional
public class DrinkServiceImpl implements DrinkService {

    private final Logger log = LoggerFactory.getLogger(DrinkServiceImpl.class);

    private final DrinkRepository drinkRepository;

    public DrinkServiceImpl(DrinkRepository drinkRepository) {
        this.drinkRepository = drinkRepository;
    }

    /**
     * Save a drink.
     *
     * @param drink the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Drink save(Drink drink) {
        log.debug("Request to save Drink : {}", drink);
        return drinkRepository.save(drink);
    }

    /**
     * Get all the drinks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Drink> findAll(Pageable pageable) {
        log.debug("Request to get all Drinks");
        return drinkRepository.findAll(pageable);
    }


    /**
     * Get one drink by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Drink> findOne(Long id) {
        log.debug("Request to get Drink : {}", id);
        return drinkRepository.findById(id);
    }

    /**
     * Delete the drink by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Drink : {}", id);
        drinkRepository.deleteById(id);
    }
    @Override
   public Drink partialSave(Drink partialDrink) {
       log.debug("Request to save Drink : {}", partialDrink);
       Optional<Drink> drink = drinkRepository.findById(partialDrink.getId());
       if (partialDrink.getDrinkID() == null) {
           partialDrink.setDrinkID(drink.get().getDrinkID());
       }
       if (partialDrink.getDescription() == null) {
           partialDrink.setDescription(drink.get().getDescription());
       }
       if (partialDrink.getPrice() == null) {
           partialDrink.setPrice(drink.get().getPrice());
       }
       if (partialDrink.getRestaurantName() == null) {
           partialDrink.setRestaurantName(drink.get().getRestaurantName());
       }
       return drinkRepository.save(partialDrink);
   }

}
