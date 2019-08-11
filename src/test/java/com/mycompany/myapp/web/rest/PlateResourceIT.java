package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.ProyectoAPiRestApp;
import com.mycompany.myapp.domain.Plate;
import com.mycompany.myapp.repository.PlateRepository;
import com.mycompany.myapp.service.PlateService;
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
 * Integration tests for the {@link PlateResource} REST controller.
 */
@SpringBootTest(classes = ProyectoAPiRestApp.class)
public class PlateResourceIT {

    private static final String DEFAULT_PLATE_ID = "AAAAAAAAAA";
    private static final String UPDATED_PLATE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_PRICE = 1L;
    private static final Long UPDATED_PRICE = 2L;
    private static final Long SMALLER_PRICE = 1L - 1L;

    @Autowired
    private PlateRepository plateRepository;

    @Autowired
    private PlateService plateService;

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

    private MockMvc restPlateMockMvc;

    private Plate plate;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PlateResource plateResource = new PlateResource(plateService);
        this.restPlateMockMvc = MockMvcBuilders.standaloneSetup(plateResource)
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
    public static Plate createEntity(EntityManager em) {
        Plate plate = new Plate()
            .plateID(DEFAULT_PLATE_ID)
            .description(DEFAULT_DESCRIPTION)
            .price(DEFAULT_PRICE);
        return plate;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Plate createUpdatedEntity(EntityManager em) {
        Plate plate = new Plate()
            .plateID(UPDATED_PLATE_ID)
            .description(UPDATED_DESCRIPTION)
            .price(UPDATED_PRICE);
        return plate;
    }

    @BeforeEach
    public void initTest() {
        plate = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlate() throws Exception {
        int databaseSizeBeforeCreate = plateRepository.findAll().size();

        // Create the Plate
        restPlateMockMvc.perform(post("/api/plates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plate)))
            .andExpect(status().isCreated());

        // Validate the Plate in the database
        List<Plate> plateList = plateRepository.findAll();
        assertThat(plateList).hasSize(databaseSizeBeforeCreate + 1);
        Plate testPlate = plateList.get(plateList.size() - 1);
        assertThat(testPlate.getPlateID()).isEqualTo(DEFAULT_PLATE_ID);
        assertThat(testPlate.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPlate.getPrice()).isEqualTo(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    public void createPlateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = plateRepository.findAll().size();

        // Create the Plate with an existing ID
        plate.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlateMockMvc.perform(post("/api/plates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plate)))
            .andExpect(status().isBadRequest());

        // Validate the Plate in the database
        List<Plate> plateList = plateRepository.findAll();
        assertThat(plateList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPlateIDIsRequired() throws Exception {
        int databaseSizeBeforeTest = plateRepository.findAll().size();
        // set the field null
        plate.setPlateID(null);

        // Create the Plate, which fails.

        restPlateMockMvc.perform(post("/api/plates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plate)))
            .andExpect(status().isBadRequest());

        List<Plate> plateList = plateRepository.findAll();
        assertThat(plateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPlates() throws Exception {
        // Initialize the database
        plateRepository.saveAndFlush(plate);

        // Get all the plateList
        restPlateMockMvc.perform(get("/api/plates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(plate.getId().intValue())))
            .andExpect(jsonPath("$.[*].plateID").value(hasItem(DEFAULT_PLATE_ID.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())));
    }
    
    @Test
    @Transactional
    public void getPlate() throws Exception {
        // Initialize the database
        plateRepository.saveAndFlush(plate);

        // Get the plate
        restPlateMockMvc.perform(get("/api/plates/{id}", plate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(plate.getId().intValue()))
            .andExpect(jsonPath("$.plateID").value(DEFAULT_PLATE_ID.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPlate() throws Exception {
        // Get the plate
        restPlateMockMvc.perform(get("/api/plates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlate() throws Exception {
        // Initialize the database
        plateService.save(plate);

        int databaseSizeBeforeUpdate = plateRepository.findAll().size();

        // Update the plate
        Plate updatedPlate = plateRepository.findById(plate.getId()).get();
        // Disconnect from session so that the updates on updatedPlate are not directly saved in db
        em.detach(updatedPlate);
        updatedPlate
            .plateID(UPDATED_PLATE_ID)
            .description(UPDATED_DESCRIPTION)
            .price(UPDATED_PRICE);

        restPlateMockMvc.perform(put("/api/plates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPlate)))
            .andExpect(status().isOk());

        // Validate the Plate in the database
        List<Plate> plateList = plateRepository.findAll();
        assertThat(plateList).hasSize(databaseSizeBeforeUpdate);
        Plate testPlate = plateList.get(plateList.size() - 1);
        assertThat(testPlate.getPlateID()).isEqualTo(UPDATED_PLATE_ID);
        assertThat(testPlate.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPlate.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void updateNonExistingPlate() throws Exception {
        int databaseSizeBeforeUpdate = plateRepository.findAll().size();

        // Create the Plate

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlateMockMvc.perform(put("/api/plates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plate)))
            .andExpect(status().isBadRequest());

        // Validate the Plate in the database
        List<Plate> plateList = plateRepository.findAll();
        assertThat(plateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePlate() throws Exception {
        // Initialize the database
        plateService.save(plate);

        int databaseSizeBeforeDelete = plateRepository.findAll().size();

        // Delete the plate
        restPlateMockMvc.perform(delete("/api/plates/{id}", plate.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Plate> plateList = plateRepository.findAll();
        assertThat(plateList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Plate.class);
        Plate plate1 = new Plate();
        plate1.setId(1L);
        Plate plate2 = new Plate();
        plate2.setId(plate1.getId());
        assertThat(plate1).isEqualTo(plate2);
        plate2.setId(2L);
        assertThat(plate1).isNotEqualTo(plate2);
        plate1.setId(null);
        assertThat(plate1).isNotEqualTo(plate2);
    }
}
