package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.ProyectoAPiRestApp;
import com.mycompany.myapp.domain.Drink;
import com.mycompany.myapp.repository.DrinkRepository;
import com.mycompany.myapp.service.DrinkService;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link DrinkResource} REST controller.
 */
@SpringBootTest(classes = ProyectoAPiRestApp.class)
public class DrinkResourceIT {

    private static final String DEFAULT_DRINK_ID = "AAAAAAAAAA";
    private static final String UPDATED_DRINK_ID = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_PRICE = 1L;
    private static final Long UPDATED_PRICE = 2L;
    private static final Long SMALLER_PRICE = 1L - 1L;

    @Autowired
    private DrinkRepository drinkRepository;

    @Autowired
    private DrinkService drinkService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restDrinkMockMvc;

    private Drink drink;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DrinkResource drinkResource = new DrinkResource(drinkService);
        this.restDrinkMockMvc = MockMvcBuilders.standaloneSetup(drinkResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Drink createEntity(EntityManager em) {
        Drink drink = new Drink()
            .drinkID(DEFAULT_DRINK_ID)
            .description(DEFAULT_DESCRIPTION)
            .price(DEFAULT_PRICE);
        return drink;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Drink createUpdatedEntity(EntityManager em) {
        Drink drink = new Drink()
            .drinkID(UPDATED_DRINK_ID)
            .description(UPDATED_DESCRIPTION)
            .price(UPDATED_PRICE);
        return drink;
    }

    @BeforeEach
    public void initTest() {
        drink = createEntity(em);
    }

    @Test
    @Transactional
    public void createDrink() throws Exception {
        int databaseSizeBeforeCreate = drinkRepository.findAll().size();

        // Create the Drink
        restDrinkMockMvc.perform(post("/api/drinks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(drink)))
            .andExpect(status().isCreated());

        // Validate the Drink in the database
        List<Drink> drinkList = drinkRepository.findAll();
        assertThat(drinkList).hasSize(databaseSizeBeforeCreate + 1);
        Drink testDrink = drinkList.get(drinkList.size() - 1);
        assertThat(testDrink.getDrinkID()).isEqualTo(DEFAULT_DRINK_ID);
        assertThat(testDrink.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDrink.getPrice()).isEqualTo(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    public void createDrinkWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = drinkRepository.findAll().size();

        // Create the Drink with an existing ID
        drink.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDrinkMockMvc.perform(post("/api/drinks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(drink)))
            .andExpect(status().isBadRequest());

        // Validate the Drink in the database
        List<Drink> drinkList = drinkRepository.findAll();
        assertThat(drinkList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDrinkIDIsRequired() throws Exception {
        int databaseSizeBeforeTest = drinkRepository.findAll().size();
        // set the field null
        drink.setDrinkID(null);

        // Create the Drink, which fails.

        restDrinkMockMvc.perform(post("/api/drinks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(drink)))
            .andExpect(status().isBadRequest());

        List<Drink> drinkList = drinkRepository.findAll();
        assertThat(drinkList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDrinks() throws Exception {
        // Initialize the database
        drinkRepository.saveAndFlush(drink);

        // Get all the drinkList
        restDrinkMockMvc.perform(get("/api/drinks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(drink.getId().intValue())))
            .andExpect(jsonPath("$.[*].drinkID").value(hasItem(DEFAULT_DRINK_ID.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())));
    }
    
    @Test
    @Transactional
    public void getDrink() throws Exception {
        // Initialize the database
        drinkRepository.saveAndFlush(drink);

        // Get the drink
        restDrinkMockMvc.perform(get("/api/drinks/{id}", drink.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(drink.getId().intValue()))
            .andExpect(jsonPath("$.drinkID").value(DEFAULT_DRINK_ID.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDrink() throws Exception {
        // Get the drink
        restDrinkMockMvc.perform(get("/api/drinks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDrink() throws Exception {
        // Initialize the database
        drinkService.save(drink);

        int databaseSizeBeforeUpdate = drinkRepository.findAll().size();

        // Update the drink
        Drink updatedDrink = drinkRepository.findById(drink.getId()).get();
        // Disconnect from session so that the updates on updatedDrink are not directly saved in db
        em.detach(updatedDrink);
        updatedDrink
            .drinkID(UPDATED_DRINK_ID)
            .description(UPDATED_DESCRIPTION)
            .price(UPDATED_PRICE);

        restDrinkMockMvc.perform(put("/api/drinks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDrink)))
            .andExpect(status().isOk());

        // Validate the Drink in the database
        List<Drink> drinkList = drinkRepository.findAll();
        assertThat(drinkList).hasSize(databaseSizeBeforeUpdate);
        Drink testDrink = drinkList.get(drinkList.size() - 1);
        assertThat(testDrink.getDrinkID()).isEqualTo(UPDATED_DRINK_ID);
        assertThat(testDrink.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDrink.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void updateNonExistingDrink() throws Exception {
        int databaseSizeBeforeUpdate = drinkRepository.findAll().size();

        // Create the Drink

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDrinkMockMvc.perform(put("/api/drinks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(drink)))
            .andExpect(status().isBadRequest());

        // Validate the Drink in the database
        List<Drink> drinkList = drinkRepository.findAll();
        assertThat(drinkList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDrink() throws Exception {
        // Initialize the database
        drinkService.save(drink);

        int databaseSizeBeforeDelete = drinkRepository.findAll().size();

        // Delete the drink
        restDrinkMockMvc.perform(delete("/api/drinks/{id}", drink.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Drink> drinkList = drinkRepository.findAll();
        assertThat(drinkList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Drink.class);
        Drink drink1 = new Drink();
        drink1.setId(1L);
        Drink drink2 = new Drink();
        drink2.setId(drink1.getId());
        assertThat(drink1).isEqualTo(drink2);
        drink2.setId(2L);
        assertThat(drink1).isNotEqualTo(drink2);
        drink1.setId(null);
        assertThat(drink1).isNotEqualTo(drink2);
    }
}
