package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Dessert;
import com.mycompany.myapp.service.DessertService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Dessert}.
 */
@RestController
@RequestMapping("/api")
public class DessertResource {

    private final Logger log = LoggerFactory.getLogger(DessertResource.class);

    private static final String ENTITY_NAME = "dessert";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DessertService dessertService;

    public DessertResource(DessertService dessertService) {
        this.dessertService = dessertService;
    }

    /**
     * {@code POST  /desserts} : Create a new dessert.
     *
     * @param dessert the dessert to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dessert, or with status {@code 400 (Bad Request)} if the dessert has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/desserts")
    public ResponseEntity<Dessert> createDessert(@Valid @RequestBody Dessert dessert) throws URISyntaxException {
        log.debug("REST request to save Dessert : {}", dessert);
        if (dessert.getId() != null) {
            throw new BadRequestAlertException("A new dessert cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Dessert result = dessertService.save(dessert);
        return ResponseEntity.created(new URI("/api/desserts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /desserts} : Updates an existing dessert.
     *
     * @param dessert the dessert to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dessert,
     * or with status {@code 400 (Bad Request)} if the dessert is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dessert couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/desserts/{id}")
    public ResponseEntity<Dessert> updateDessert(@PathVariable Long id,@Valid @RequestBody Dessert dessert) throws URISyntaxException {
        log.debug("REST request to update Dessert : {}", dessert);
        //if (dessert.getId() == null) {
            if (id == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
          dessert.setId(id);
        Dessert result = dessertService.save(dessert);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dessert.getId().toString()))
            .body(result);
    }
    /**
  * PATCH  /restaurants/:id : Updates an existing area.
  *
  * @param restaurant the restaurant to update
  * @return the ResponseEntity with status 200 (OK) and with body the updated area,
  * or with status 400 (Bad Request) if the area is not valid,
  * or with status 500 (Internal Server Error) if the area couldn't be updated
  * @throws URISyntaxException if the Location URI syntax is incorrect
  */
 @PatchMapping("/Desserts/{id}")
 public ResponseEntity<Dessert> partialUpdateDessert(@PathVariable Long id, @RequestBody Dessert dessert)
         throws URISyntaxException {
     log.debug("REST request to update Dessert : {}", dessert);
     if (id == null) {
         throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
     }
     dessert.setId(id);
     Dessert result = dessertService.partialSave(dessert);
     return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, true,ENTITY_NAME, dessert.getId().toString()))
             .body(result);
 }

    /**
     * {@code GET  /desserts} : get all the desserts.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of desserts in body.
     */
    @GetMapping("/desserts")
    public ResponseEntity<List<Dessert>> getAllDesserts(Pageable pageable) {
        log.debug("REST request to get a page of Desserts");
        Page<Dessert> page = dessertService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /desserts/:id} : get the "id" dessert.
     *
     * @param id the id of the dessert to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dessert, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/desserts/{id}")
    public ResponseEntity<Dessert> getDessert(@PathVariable Long id) {
        log.debug("REST request to get Dessert : {}", id);
        Optional<Dessert> dessert = dessertService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dessert);
    }

    /**
     * {@code DELETE  /desserts/:id} : delete the "id" dessert.
     *
     * @param id the id of the dessert to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/desserts/{id}")
    public ResponseEntity<Void> deleteDessert(@PathVariable Long id) {
        log.debug("REST request to delete Dessert : {}", id);
        dessertService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
