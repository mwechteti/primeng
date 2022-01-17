package com.teamwill.rmkpro.web.rest;

import com.teamwill.rmkpro.IntegrationTest;
import com.teamwill.rmkpro.domain.Vehicle;
import com.teamwill.rmkpro.enums.FuelType;
import com.teamwill.rmkpro.repository.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link VehicleResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VehicleResourceIT {

    private static final String DEFAULT_REGISTRATION_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_REGISTRATION_NUMBER = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FIRST_REGISTRATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FIRST_REGISTRATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_USED = false;
    private static final Boolean UPDATED_USED = true;

    private static final Integer DEFAULT_MILEAGE = 1;
    private static final Integer UPDATED_MILEAGE = 2;

    private static final String DEFAULT_MILEAGE_UNIT = "AAAAAAAAAA";
    private static final String UPDATED_MILEAGE_UNIT = "BBBBBBBBBB";

    private static final FuelType DEFAULT_FUEL_TYPE = FuelType.PETROL;
    private static final FuelType UPDATED_FUEL_TYPE = FuelType.ELECTRIC;

    private static final String DEFAULT_VIN = "AAAAAAAAAA";
    private static final String UPDATED_VIN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/vehicles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVehicleMockMvc;

    private Vehicle vehicle;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vehicle createEntity(EntityManager em) {
        Vehicle vehicle = new Vehicle()
            .plateNumber(DEFAULT_REGISTRATION_NUMBER)
            .firstRegistrationDate(DEFAULT_FIRST_REGISTRATION_DATE)
            .used(DEFAULT_USED)
            .mileage(DEFAULT_MILEAGE)
            .mileageUnit(DEFAULT_MILEAGE_UNIT)
            .fuelType(DEFAULT_FUEL_TYPE)
            .vin(DEFAULT_VIN);
        return vehicle;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vehicle createUpdatedEntity(EntityManager em) {
        Vehicle vehicle = new Vehicle()
            .plateNumber(UPDATED_REGISTRATION_NUMBER)
            .firstRegistrationDate(UPDATED_FIRST_REGISTRATION_DATE)
            .used(UPDATED_USED)
            .mileage(UPDATED_MILEAGE)
            .mileageUnit(UPDATED_MILEAGE_UNIT)
            .fuelType(UPDATED_FUEL_TYPE)
            .vin(UPDATED_VIN);
        return vehicle;
    }

    @BeforeEach
    public void initTest() {
        vehicle = createEntity(em);
    }

    @Test
    @Transactional
    void createVehicle() throws Exception {
        int databaseSizeBeforeCreate = vehicleRepository.findAll().size();
        // Create the Vehicle
        restVehicleMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicle))
            )
            .andExpect(status().isCreated());

        // Validate the Vehicle in the database
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeCreate + 1);
        Vehicle testVehicle = vehicleList.get(vehicleList.size() - 1);
        assertThat(testVehicle.getPlateNumber()).isEqualTo(DEFAULT_REGISTRATION_NUMBER);
        assertThat(testVehicle.getFirstRegistrationDate()).isEqualTo(DEFAULT_FIRST_REGISTRATION_DATE);
        assertThat(testVehicle.getUsed()).isEqualTo(DEFAULT_USED);
        assertThat(testVehicle.getMileage()).isEqualTo(DEFAULT_MILEAGE);
        assertThat(testVehicle.getMileageUnit()).isEqualTo(DEFAULT_MILEAGE_UNIT);
        assertThat(testVehicle.getFuelType()).isEqualTo(DEFAULT_FUEL_TYPE);
        assertThat(testVehicle.getVin()).isEqualTo(DEFAULT_VIN);
    }

    @Test
    @Transactional
    void createVehicleWithExistingId() throws Exception {
        // Create the Vehicle with an existing ID
        vehicle.setId(1L);

        int databaseSizeBeforeCreate = vehicleRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVehicleMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicle))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vehicle in the database
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkplateNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleRepository.findAll().size();
        // set the field null
        vehicle.setPlateNumber(null);

        // Create the Vehicle, which fails.

        restVehicleMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicle))
            )
            .andExpect(status().isBadRequest());

        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFirstRegistrationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleRepository.findAll().size();
        // set the field null
        vehicle.setFirstRegistrationDate(null);

        // Create the Vehicle, which fails.

        restVehicleMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicle))
            )
            .andExpect(status().isBadRequest());

        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUsedIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleRepository.findAll().size();
        // set the field null
        vehicle.setUsed(null);

        // Create the Vehicle, which fails.

        restVehicleMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicle))
            )
            .andExpect(status().isBadRequest());

        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMileageIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleRepository.findAll().size();
        // set the field null
        vehicle.setMileage(null);

        // Create the Vehicle, which fails.

        restVehicleMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicle))
            )
            .andExpect(status().isBadRequest());

        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMileageUnitIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleRepository.findAll().size();
        // set the field null
        vehicle.setMileageUnit(null);

        // Create the Vehicle, which fails.

        restVehicleMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicle))
            )
            .andExpect(status().isBadRequest());

        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFuelTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleRepository.findAll().size();
        // set the field null
        vehicle.setFuelType(null);

        // Create the Vehicle, which fails.

        restVehicleMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicle))
            )
            .andExpect(status().isBadRequest());

        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllVehicles() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList
        restVehicleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehicle.getId().intValue())))
            .andExpect(jsonPath("$.[*].plateNumber").value(hasItem(DEFAULT_REGISTRATION_NUMBER)))
            .andExpect(jsonPath("$.[*].firstRegistrationDate").value(hasItem(DEFAULT_FIRST_REGISTRATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].used").value(hasItem(DEFAULT_USED)))
            .andExpect(jsonPath("$.[*].mileage").value(hasItem(DEFAULT_MILEAGE)))
            .andExpect(jsonPath("$.[*].mileageUnit").value(hasItem(DEFAULT_MILEAGE_UNIT)))
            .andExpect(jsonPath("$.[*].fuelType").value(hasItem(DEFAULT_FUEL_TYPE)))
            .andExpect(jsonPath("$.[*].vin").value(hasItem(DEFAULT_VIN)));
    }

    @Test
    @Transactional
    void getVehicle() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get the vehicle
        restVehicleMockMvc
            .perform(get(ENTITY_API_URL_ID, vehicle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vehicle.getId().intValue()))
            .andExpect(jsonPath("$.plateNumber").value(DEFAULT_REGISTRATION_NUMBER))
            .andExpect(jsonPath("$.firstRegistrationDate").value(DEFAULT_FIRST_REGISTRATION_DATE.toString()))
            .andExpect(jsonPath("$.used").value(DEFAULT_USED.booleanValue()))
            .andExpect(jsonPath("$.mileage").value(DEFAULT_MILEAGE))
            .andExpect(jsonPath("$.mileageUnit").value(DEFAULT_MILEAGE_UNIT))
            .andExpect(jsonPath("$.fuelType").value(DEFAULT_FUEL_TYPE))
            .andExpect(jsonPath("$.vin").value(DEFAULT_VIN));
    }

    @Test
    @Transactional
    void getNonExistingVehicle() throws Exception {
        // Get the vehicle
        restVehicleMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewVehicle() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        int databaseSizeBeforeUpdate = vehicleRepository.findAll().size();

        // Update the vehicle
        Vehicle updatedVehicle = vehicleRepository.findById(vehicle.getId()).get();
        // Disconnect from session so that the updates on updatedVehicle are not directly saved in db
        em.detach(updatedVehicle);
        updatedVehicle
            .plateNumber(UPDATED_REGISTRATION_NUMBER)
            .firstRegistrationDate(UPDATED_FIRST_REGISTRATION_DATE)
            .used(UPDATED_USED)
            .mileage(UPDATED_MILEAGE)
            .mileageUnit(UPDATED_MILEAGE_UNIT)
            .fuelType(UPDATED_FUEL_TYPE)
            .vin(UPDATED_VIN);

        restVehicleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVehicle.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedVehicle))
            )
            .andExpect(status().isOk());

        // Validate the Vehicle in the database
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeUpdate);
        Vehicle testVehicle = vehicleList.get(vehicleList.size() - 1);
        assertThat(testVehicle.getPlateNumber()).isEqualTo(UPDATED_REGISTRATION_NUMBER);
        assertThat(testVehicle.getFirstRegistrationDate()).isEqualTo(UPDATED_FIRST_REGISTRATION_DATE);
        assertThat(testVehicle.getUsed()).isEqualTo(UPDATED_USED);
        assertThat(testVehicle.getMileage()).isEqualTo(UPDATED_MILEAGE);
        assertThat(testVehicle.getMileageUnit()).isEqualTo(UPDATED_MILEAGE_UNIT);
        assertThat(testVehicle.getFuelType()).isEqualTo(UPDATED_FUEL_TYPE);
        assertThat(testVehicle.getVin()).isEqualTo(UPDATED_VIN);
    }

    @Test
    @Transactional
    void putNonExistingVehicle() throws Exception {
        int databaseSizeBeforeUpdate = vehicleRepository.findAll().size();
        vehicle.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVehicleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vehicle.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicle))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vehicle in the database
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVehicle() throws Exception {
        int databaseSizeBeforeUpdate = vehicleRepository.findAll().size();
        vehicle.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehicleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicle))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vehicle in the database
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVehicle() throws Exception {
        int databaseSizeBeforeUpdate = vehicleRepository.findAll().size();
        vehicle.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehicleMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vehicle))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vehicle in the database
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVehicleWithPatch() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        int databaseSizeBeforeUpdate = vehicleRepository.findAll().size();

        // Update the vehicle using partial update
        Vehicle partialUpdatedVehicle = new Vehicle();
        partialUpdatedVehicle.setId(vehicle.getId());

        partialUpdatedVehicle.mileage(UPDATED_MILEAGE).mileageUnit(UPDATED_MILEAGE_UNIT).vin(UPDATED_VIN);

        restVehicleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVehicle.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVehicle))
            )
            .andExpect(status().isOk());

        // Validate the Vehicle in the database
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeUpdate);
        Vehicle testVehicle = vehicleList.get(vehicleList.size() - 1);
        assertThat(testVehicle.getPlateNumber()).isEqualTo(DEFAULT_REGISTRATION_NUMBER);
        assertThat(testVehicle.getFirstRegistrationDate()).isEqualTo(DEFAULT_FIRST_REGISTRATION_DATE);
        assertThat(testVehicle.getUsed()).isEqualTo(DEFAULT_USED);
        assertThat(testVehicle.getMileage()).isEqualTo(UPDATED_MILEAGE);
        assertThat(testVehicle.getMileageUnit()).isEqualTo(UPDATED_MILEAGE_UNIT);
        assertThat(testVehicle.getFuelType()).isEqualTo(DEFAULT_FUEL_TYPE);
        assertThat(testVehicle.getVin()).isEqualTo(UPDATED_VIN);
    }

    @Test
    @Transactional
    void fullUpdateVehicleWithPatch() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        int databaseSizeBeforeUpdate = vehicleRepository.findAll().size();

        // Update the vehicle using partial update
        Vehicle partialUpdatedVehicle = new Vehicle();
        partialUpdatedVehicle.setId(vehicle.getId());

        partialUpdatedVehicle
            .plateNumber(UPDATED_REGISTRATION_NUMBER)
            .firstRegistrationDate(UPDATED_FIRST_REGISTRATION_DATE)
            .used(UPDATED_USED)
            .mileage(UPDATED_MILEAGE)
            .mileageUnit(UPDATED_MILEAGE_UNIT)
            .fuelType(UPDATED_FUEL_TYPE)
            .vin(UPDATED_VIN);

        restVehicleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVehicle.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVehicle))
            )
            .andExpect(status().isOk());

        // Validate the Vehicle in the database
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeUpdate);
        Vehicle testVehicle = vehicleList.get(vehicleList.size() - 1);
        assertThat(testVehicle.getPlateNumber()).isEqualTo(UPDATED_REGISTRATION_NUMBER);
        assertThat(testVehicle.getFirstRegistrationDate()).isEqualTo(UPDATED_FIRST_REGISTRATION_DATE);
        assertThat(testVehicle.getUsed()).isEqualTo(UPDATED_USED);
        assertThat(testVehicle.getMileage()).isEqualTo(UPDATED_MILEAGE);
        assertThat(testVehicle.getMileageUnit()).isEqualTo(UPDATED_MILEAGE_UNIT);
        assertThat(testVehicle.getFuelType()).isEqualTo(UPDATED_FUEL_TYPE);
        assertThat(testVehicle.getVin()).isEqualTo(UPDATED_VIN);
    }

    @Test
    @Transactional
    void patchNonExistingVehicle() throws Exception {
        int databaseSizeBeforeUpdate = vehicleRepository.findAll().size();
        vehicle.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVehicleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, vehicle.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vehicle))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vehicle in the database
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVehicle() throws Exception {
        int databaseSizeBeforeUpdate = vehicleRepository.findAll().size();
        vehicle.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehicleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vehicle))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vehicle in the database
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVehicle() throws Exception {
        int databaseSizeBeforeUpdate = vehicleRepository.findAll().size();
        vehicle.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehicleMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vehicle))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vehicle in the database
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVehicle() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        int databaseSizeBeforeDelete = vehicleRepository.findAll().size();

        // Delete the vehicle
        restVehicleMockMvc
            .perform(delete(ENTITY_API_URL_ID, vehicle.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
