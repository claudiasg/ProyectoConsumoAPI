package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.RestaurantService;
import com.mycompany.myapp.domain.Restaurant;
import com.mycompany.myapp.repository.RestaurantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Restaurant}.
 */
@Service
@Transactional
public class RestaurantServiceImpl implements RestaurantService {

    private final Logger log = LoggerFactory.getLogger(RestaurantServiceImpl.class);

    private final RestaurantRepository restaurantRepository;

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    /**
     * Save a restaurant.
     *
     * @param restaurant the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Restaurant save(Restaurant restaurant) {
        log.debug("Request to save Restaurant : {}", restaurant);
        return restaurantRepository.save(restaurant);
    }

    /**
     * Get all the restaurants.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Restaurant> findAll(Pageable pageable) {
        log.debug("Request to get all Restaurants");
        return restaurantRepository.findAll(pageable);
    }


    /**
     * Get one restaurant by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Restaurant> findOne(Long id) {
        log.debug("Request to get Restaurant : {}", id);
        return restaurantRepository.findById(id);
    }

    /**
     * Delete the restaurant by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Restaurant : {}", id);
        restaurantRepository.deleteById(id);
    }
    @Override
   public Restaurant partialSave(Restaurant partialRestaurant) {
       log.debug("Request to save Restaurant : {}", partialRestaurant);
       Optional<Restaurant> restaurant = restaurantRepository.findById(partialRestaurant.getId());
       if (partialRestaurant.getRestaurantID() == null) {
           partialRestaurant.setRestaurantID(restaurant.get().getRestaurantID());
       }
       if (partialRestaurant.getRestaurantName() == null) {
           partialRestaurant.setRestaurantName(restaurant.get().getRestaurantName());
       }
       if (partialRestaurant.getAddress() == null) {
           partialRestaurant.setAddress(restaurant.get().getAddress());
       }
       if (partialRestaurant.getPlates() == null) {
           partialRestaurant.setPlates(restaurant.get().getPlates());
       }
       if (partialRestaurant.getDrinks() == null) {
           partialRestaurant.setDrinks(restaurant.get().getDrinks());
       }
       if (partialRestaurant.getDesserts() == null) {
           partialRestaurant.setDesserts(restaurant.get().getDesserts());
       }
       return restaurantRepository.save(partialRestaurant);
   }

}
