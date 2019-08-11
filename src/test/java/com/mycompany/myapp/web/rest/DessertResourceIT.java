package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.ProyectoAPiRestApp;
import com.mycompany.myapp.domain.Dessert;
import com.mycompany.myapp.repository.DessertRepository;
import com.mycompany.myapp.service.DessertService;
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
 * Integration tests for the {@link DessertResource} REST controller.
 */
@SpringBootTest(classes = ProyectoAPiRestApp.class)
public class DessertResourceIT {

    private static final String DEFAULT_DESSERT_ID = "AAAAAAAAAA";
    private static final String UPDATED_DESSERT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_PRICE = 1L;
    private static final Long UPDATED_PRICE = 2L;
    private static final Long SMALLER_PRICE = 1L - 1L;

    @Autowired
    private DessertRepository dessertRepository;

    @Autowired
    private DessertService dessertService;

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

    private MockMvc restDessertMockMvc;

    private Dessert dessert;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DessertResource dessertResource = new DessertResource(dessertService);
        this.restDessertMockMvc = MockMvcBuilders.standaloneSetup(dessertResource)
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
    public static Dessert createEntity(EntityManager em) {
        Dessert dessert = new Dessert()
            .dessertID(DEFAULT_DESSERT_ID)
            .description(DEFAULT_DESCRIPTION)
            .price(DEFAULT_PRICE);
        return dessert;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dessert createUpdatedEntity(EntityManager em) {
        Dessert dessert = new Dessert()
            .dessertID(UPDATED_DESSERT_ID)
            .description(UPDATED_DESCRIPTION)
            .price(UPDATED_PRICE);
        return dessert;
    }

    @BeforeEach
    public void initTest() {
        dessert = createEntity(em);
    }

    @Test
    @Transactional
    public void createDessert() throws Exception {
        int databaseSizeBeforeCreate = dessertRepository.findAll().size();

        // Create the Dessert
        restDessertMockMvc.perform(post("/api/desserts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dessert)))
            .andExpect(status().isCreated());

        // Validate the Dessert in the database
        List<Dessert> dessertList = dessertRepository.findAll();
        assertThat(dessertList).hasSize(databaseSizeBeforeCreate + 1);
        Dessert testDessert = dessertList.get(dessertList.size() - 1);
        assertThat(testDessert.getDessertID()).isEqualTo(DEFAULT_DESSERT_ID);
        assertThat(testDessert.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDessert.getPrice()).isEqualTo(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    public void createDessertWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dessertRepository.findAll().size();

        // Create the Dessert with an existing ID
        dessert.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDessertMockMvc.perform(post("/api/desserts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dessert)))
            .andExpect(status().isBadRequest());

        // Validate the Dessert in the database
        List<Dessert> dessertList = dessertRepository.findAll();
        assertThat(dessertList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDessertIDIsRequired() throws Exception {
        int databaseSizeBeforeTest = dessertRepository.findAll().size();
        // set the field null
        dessert.setDessertID(null);

        // Create the Dessert, which fails.

        restDessertMockMvc.perform(post("/api/desserts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dessert)))
            .andExpect(status().isBadRequest());

        List<Dessert> dessertList = dessertRepository.findAll();
        assertThat(dessertList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDesserts() throws Exception {
        // Initialize the database
        dessertRepository.saveAndFlush(dessert);

        // Get all the dessertList
        restDessertMockMvc.perform(get("/api/desserts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dessert.getId().intValue())))
            .andExpect(jsonPath("$.[*].dessertID").value(hasItem(DEFAULT_DESSERT_ID.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())));
    }
    
    @Test
    @Transactional
    public void getDessert() throws Exception {
        // Initialize the database
        dessertRepository.saveAndFlush(dessert);

        // Get the dessert
        restDessertMockMvc.perform(get("/api/desserts/{id}", dessert.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dessert.getId().intValue()))
            .andExpect(jsonPath("$.dessertID").value(DEFAULT_DESSERT_ID.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDessert() throws Exception {
        // Get the dessert
        restDessertMockMvc.perform(get("/api/desserts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDessert() throws Exception {
        // Initialize the database
        dessertService.save(dessert);

        int databaseSizeBeforeUpdate = dessertRepository.findAll().size();

        // Update the dessert
        Dessert updatedDessert = dessertRepository.findById(dessert.getId()).get();
        // Disconnect from session so that the updates on updatedDessert are not directly saved in db
        em.detach(updatedDessert);
        updatedDessert
            .dessertID(UPDATED_DESSERT_ID)
            .description(UPDATED_DESCRIPTION)
            .price(UPDATED_PRICE);

        restDessertMockMvc.perform(put("/api/desserts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDessert)))
            .andExpect(status().isOk());

        // Validate the Dessert in the database
        List<Dessert> dessertList = dessertRepository.findAll();
        assertThat(dessertList).hasSize(databaseSizeBeforeUpdate);
        Dessert testDessert = dessertList.get(dessertList.size() - 1);
        assertThat(testDessert.getDessertID()).isEqualTo(UPDATED_DESSERT_ID);
        assertThat(testDessert.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDessert.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void updateNonExistingDessert() throws Exception {
        int databaseSizeBeforeUpdate = dessertRepository.findAll().size();

        // Create the Dessert

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDessertMockMvc.perform(put("/api/desserts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dessert)))
            .andExpect(status().isBadRequest());

        // Validate the Dessert in the database
        List<Dessert> dessertList = dessertRepository.findAll();
        assertThat(dessertList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDessert() throws Exception {
        // Initialize the database
        dessertService.save(dessert);

        int databaseSizeBeforeDelete = dessertRepository.findAll().size();

        // Delete the dessert
        restDessertMockMvc.perform(delete("/api/desserts/{id}", dessert.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Dessert> dessertList = dessertRepository.findAll();
        assertThat(dessertList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Dessert.class);
        Dessert dessert1 = new Dessert();
        dessert1.setId(1L);
        Dessert dessert2 = new Dessert();
        dessert2.setId(dessert1.getId());
        assertThat(dessert1).isEqualTo(dessert2);
        dessert2.setId(2L);
        assertThat(dessert1).isNotEqualTo(dessert2);
        dessert1.setId(null);
        assertThat(dessert1).isNotEqualTo(dessert2);
    }
}
