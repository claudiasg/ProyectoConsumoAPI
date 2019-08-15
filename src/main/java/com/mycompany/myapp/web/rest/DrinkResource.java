package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Drink;
import com.mycompany.myapp.service.DrinkService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Drink}.
 */
@RestController
@RequestMapping("/api")
public class DrinkResource {

    private final Logger log = LoggerFactory.getLogger(DrinkResource.class);

    private static final String ENTITY_NAME = "drink";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DrinkService drinkService;

    public DrinkResource(DrinkService drinkService) {
        this.drinkService = drinkService;
    }

    /**
     * {@code POST  /drinks} : Create a new drink.
     *
     * @param drink the drink to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new drink, or with status {@code 400 (Bad Request)} if the drink has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/drinks")
    public ResponseEntity<Drink> createDrink(@Valid @RequestBody Drink drink) throws URISyntaxException {
        log.debug("REST request to save Drink : {}", drink);
        if (drink.getId() != null) {
            throw new BadRequestAlertException("A new drink cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Drink result = drinkService.save(drink);
        return ResponseEntity.created(new URI("/api/drinks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /drinks} : Updates an existing drink.
     *
     * @param drink the drink to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated drink,
     * or with status {@code 400 (Bad Request)} if the drink is not valid,
     * or with status {@code 500 (Internal Server Error)} if the drink couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
     @PutMapping("/drinks/{id}")
     public ResponseEntity<Drink> updateDrink(@PathVariable Long id,@Valid @RequestBody Drink drink) throws URISyntaxException {
         log.debug("REST request to update Drink : {}", drink);
         //if (drink.getId() == null) {
           if (id == null) {
             throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
         }
           drink.setId(id);
         Drink result = drinkService.save(drink);
         return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, true,ENTITY_NAME, drink.getId().toString()))
             .body(result);
     }
    /**
     * {@code GET  /drinks} : get all the drinks.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of drinks in body.
     */
    @GetMapping("/drinks")
    public ResponseEntity<List<Drink>> getAllDrinks(Pageable pageable) {
        log.debug("REST request to get a page of Drinks");
        Page<Drink> page = drinkService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /drinks/:id} : get the "id" drink.
     *
     * @param id the id of the drink to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the drink, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/drinks/{id}")
    public ResponseEntity<Drink> getDrink(@PathVariable Long id) {
        log.debug("REST request to get Drink : {}", id);
        Optional<Drink> drink = drinkService.findOne(id);
        return ResponseUtil.wrapOrNotFound(drink);
    }

    /**
     * {@code DELETE  /drinks/:id} : delete the "id" drink.
     *
     * @param id the id of the drink to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/drinks/{id}")
    public ResponseEntity<Void> deleteDrink(@PathVariable Long id) {
        log.debug("REST request to delete Drink : {}", id);
        drinkService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
