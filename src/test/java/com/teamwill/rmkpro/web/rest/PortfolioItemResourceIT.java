package com.teamwill.rmkpro.web.rest;

import com.teamwill.rmkpro.IntegrationTest;
import com.teamwill.rmkpro.domain.LegalEntity;
import com.teamwill.rmkpro.domain.LegalEntityType;
import com.teamwill.rmkpro.domain.Make;
import com.teamwill.rmkpro.domain.Model;
import com.teamwill.rmkpro.domain.Portfolio;
import com.teamwill.rmkpro.domain.PortfolioItem;
import com.teamwill.rmkpro.domain.PortfolioItemStatus;
import com.teamwill.rmkpro.domain.PortfolioItemStatusEntry;
import com.teamwill.rmkpro.domain.Vehicle;
import com.teamwill.rmkpro.enums.FuelType;
import com.teamwill.rmkpro.enums.PortfolioItemStatusEnum;
import com.teamwill.rmkpro.repository.LegalEntityRepository;
import com.teamwill.rmkpro.repository.LegalEntityTypeRepository;
import com.teamwill.rmkpro.repository.ModelRepository;
import com.teamwill.rmkpro.repository.PortfolioItemRepository;
import com.teamwill.rmkpro.repository.PortfolioItemStatusEntryRepository;
import com.teamwill.rmkpro.repository.PortfolioItemStatusRepository;
import com.teamwill.rmkpro.repository.PortfolioRepository;
import com.teamwill.rmkpro.repository.VehicleRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PortfolioItemResource} REST controller.
 */
@RunWith(SpringRunner.class)
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PortfolioItemResourceIT {

    private static final String ENTITY_API_URL = "/api/portfolio-items";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PortfolioItemRepository portfolioItemRepository;

    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private LegalEntityTypeRepository legalEntityTypeRepository;

    @Autowired
    private LegalEntityRepository legalEntityRepository;

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private PortfolioItemStatusEntryRepository portfolioItemStatusEntryRepository;

    @Autowired
    private PortfolioItemStatusRepository portfolioItemStatusRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPortfolioItemMockMvc;

    private PortfolioItem portfolioItem;


    public static Vehicle createEntityVehicle() {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(10L);
        vehicle.setMileage(160000);
        vehicle.setPlateNumber("HX0VHG");
        vehicle.setFuelType(FuelType.PETROL);
        vehicle.setMileageUnit("km");
        vehicle.setFirstRegistrationDate(LocalDate.of(1987, 11, 10));
        vehicle.setUsed(true);

        return vehicle;
    }

    public static Portfolio createEntityPortfolio() {
        Portfolio portfolio = new Portfolio(1L, "TEST_PORTFOLIO");

        return portfolio;
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PortfolioItem createEntity() {
        PortfolioItem portfolioItem = new PortfolioItem();
        portfolioItem.setId(1L);

        return portfolioItem;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PortfolioItem createUpdatedEntity(EntityManager em) {
        PortfolioItem portfolioItem = new PortfolioItem();

        Make make = new Make(22L, "Toyota");
        Model model = new Model(1L, "RAV4", 1982, make);
        Vehicle vehicle = new Vehicle();
        vehicle.setId(10L);
        vehicle.setMileage(160000);
        vehicle.setPlateNumber("HX0VHG");
        vehicle.setMake(make);
        vehicle.setModel(model);
        vehicle.setFuelType(FuelType.PETROL);
        portfolioItem.setVehicle(vehicle);

        Portfolio portfolio = new Portfolio(10L, "TEST_PORTFOLIO");
        portfolioItem.setPortfolio(portfolio);

        PortfolioItemStatusEntry statusEntry1 = new PortfolioItemStatusEntry();
        statusEntry1.setId(1L);
        statusEntry1.setStatus(new PortfolioItemStatus(10, PortfolioItemStatusEnum.COLLECTED));
        statusEntry1.setStatusCreationDate(LocalDate.of(2019, 12, 23));
        PortfolioItemStatusEntry statusEntry2 = new PortfolioItemStatusEntry();
        statusEntry2.setId(2L);
        statusEntry2.setStatus(new PortfolioItemStatus(10, PortfolioItemStatusEnum.COLLECTED));
        statusEntry2.setStatusCreationDate(LocalDate.of(2020, 1, 1));
        Set<PortfolioItemStatusEntry> statusEntries = new HashSet<>();
        Collections.addAll(statusEntries, statusEntry1, statusEntry2);
        portfolioItem.setStatusEntries(statusEntries);

        return portfolioItem;
    }

    @BeforeAll
    public void setup() {
        Make make = new Make(22L, "Toyota");
        Model model = new Model(1L, "RAV4", 1982, make);
        modelRepository.saveAndFlush(model);
        LegalEntityType legalEntityType = new LegalEntityType(100L, "Fake");
        LegalEntity legalEntity = new LegalEntity();
        legalEntity.setId(1L);
        legalEntity.setName("Coca-Cola");
        legalEntity.setType(legalEntityType);
        legalEntityRepository.saveAndFlush(legalEntity);

        Vehicle vehicle = createEntityVehicle();
        vehicle.setMake(make);
        vehicle.setModel(model);
        vehicle.setOwner(legalEntity);
        vehicleRepository.saveAndFlush(vehicle);

        Portfolio portfolio = createEntityPortfolio();
        portfolioRepository.saveAndFlush(portfolio);

        portfolioItem = createEntity();
        portfolioItem.setVehicle(vehicle);
        portfolioItem.setPortfolio(portfolio);

        portfolioItemRepository.saveAndFlush(portfolioItem);
    }

    @Test
    @Transactional
    void createPortfolioItem() throws Exception {
        int databaseSizeBeforeCreate = portfolioItemRepository.findAll().size();
        // Create the PortfolioItem
        restPortfolioItemMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(portfolioItem))
            )
            .andExpect(status().isCreated());

        // Validate the PortfolioItem in the database
        List<PortfolioItem> portfolioItemList = portfolioItemRepository.findAll();
        assertThat(portfolioItemList).hasSize(databaseSizeBeforeCreate + 1);
        PortfolioItem testPortfolioItem = portfolioItemList.get(portfolioItemList.size() - 1);
    }

    @Test
    @Transactional
    void createPortfolioItemWithExistingId() throws Exception {
        // Create the PortfolioItem with an existing ID
        portfolioItem.setId(1L);

        int databaseSizeBeforeCreate = portfolioItemRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPortfolioItemMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(portfolioItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the PortfolioItem in the database
        List<PortfolioItem> portfolioItemList = portfolioItemRepository.findAll();
        assertThat(portfolioItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    //TODO to fix
    void getAllPortfolioItems() throws Exception {
//        // Initialize the database
//        portfolioItemRepository.saveAndFlush(portfolioItem);

        // Get all the portfolioItemList
        restPortfolioItemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(portfolioItem.getId().intValue())));
    }

    @Test
    @Transactional
    void getPortfolioItem() throws Exception {
        // Get the portfolioItem
        restPortfolioItemMockMvc
            .perform(get(ENTITY_API_URL_ID, portfolioItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(portfolioItem.getId().intValue())));
    }

    @Test
    @Transactional
    //TODO to fix
    void getNonExistingPortfolioItem() throws Exception {
        // Get the portfolioItem
        restPortfolioItemMockMvc.perform(get(ENTITY_API_URL_ID, 999L)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPortfolioItem() throws Exception {
        int databaseSizeBeforeUpdate = portfolioItemRepository.findAll().size();

        // Update the portfolioItem
        PortfolioItem updatedPortfolioItem = portfolioItemRepository.findById(portfolioItem.getId()).get();
        // Disconnect from session so that the updates on updatedPortfolioItem are not directly saved in db
        em.detach(updatedPortfolioItem);

        restPortfolioItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPortfolioItem.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPortfolioItem))
            )
            .andExpect(status().isOk());

        // Validate the PortfolioItem in the database
        List<PortfolioItem> portfolioItemList = portfolioItemRepository.findAll();
        assertThat(portfolioItemList).hasSize(databaseSizeBeforeUpdate);
        PortfolioItem testPortfolioItem = portfolioItemList.get(portfolioItemList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingPortfolioItem() throws Exception {
        int databaseSizeBeforeUpdate = portfolioItemRepository.findAll().size();
        portfolioItem.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPortfolioItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, portfolioItem.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(portfolioItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the PortfolioItem in the database
        List<PortfolioItem> portfolioItemList = portfolioItemRepository.findAll();
        assertThat(portfolioItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPortfolioItem() throws Exception {
        int databaseSizeBeforeUpdate = portfolioItemRepository.findAll().size();
        portfolioItem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPortfolioItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(portfolioItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the PortfolioItem in the database
        List<PortfolioItem> portfolioItemList = portfolioItemRepository.findAll();
        assertThat(portfolioItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPortfolioItem() throws Exception {
        int databaseSizeBeforeUpdate = portfolioItemRepository.findAll().size();
        portfolioItem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPortfolioItemMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(portfolioItem))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PortfolioItem in the database
        List<PortfolioItem> portfolioItemList = portfolioItemRepository.findAll();
        assertThat(portfolioItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePortfolioItemWithPatch() throws Exception {
//        // Initialize the database
//        portfolioItemRepository.saveAndFlush(portfolioItem);

        int databaseSizeBeforeUpdate = portfolioItemRepository.findAll().size();

        // Update the portfolioItem using partial update
        PortfolioItem partialUpdatedPortfolioItem = new PortfolioItem();
        partialUpdatedPortfolioItem.setId(portfolioItem.getId());

        restPortfolioItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPortfolioItem.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPortfolioItem))
            )
            .andExpect(status().isOk());

        // Validate the PortfolioItem in the database
        List<PortfolioItem> portfolioItemList = portfolioItemRepository.findAll();
        assertThat(portfolioItemList).hasSize(databaseSizeBeforeUpdate);
        PortfolioItem testPortfolioItem = portfolioItemList.get(portfolioItemList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdatePortfolioItemWithPatch() throws Exception {
//        // Initialize the database
//        portfolioItemRepository.saveAndFlush(portfolioItem);

        int databaseSizeBeforeUpdate = portfolioItemRepository.findAll().size();

        // Update the portfolioItem using partial update
        PortfolioItem partialUpdatedPortfolioItem = new PortfolioItem();
        partialUpdatedPortfolioItem.setId(portfolioItem.getId());

        restPortfolioItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPortfolioItem.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPortfolioItem))
            )
            .andExpect(status().isOk());

        // Validate the PortfolioItem in the database
        List<PortfolioItem> portfolioItemList = portfolioItemRepository.findAll();
        assertThat(portfolioItemList).hasSize(databaseSizeBeforeUpdate);
        PortfolioItem testPortfolioItem = portfolioItemList.get(portfolioItemList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingPortfolioItem() throws Exception {
        int databaseSizeBeforeUpdate = portfolioItemRepository.findAll().size();
        portfolioItem.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPortfolioItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, portfolioItem.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(portfolioItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the PortfolioItem in the database
        List<PortfolioItem> portfolioItemList = portfolioItemRepository.findAll();
        assertThat(portfolioItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPortfolioItem() throws Exception {
        int databaseSizeBeforeUpdate = portfolioItemRepository.findAll().size();
        portfolioItem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPortfolioItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(portfolioItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the PortfolioItem in the database
        List<PortfolioItem> portfolioItemList = portfolioItemRepository.findAll();
        assertThat(portfolioItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPortfolioItem() throws Exception {
        int databaseSizeBeforeUpdate = portfolioItemRepository.findAll().size();
        portfolioItem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPortfolioItemMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(portfolioItem))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PortfolioItem in the database
        List<PortfolioItem> portfolioItemList = portfolioItemRepository.findAll();
        assertThat(portfolioItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePortfolioItem() throws Exception {
//        // Initialize the database
//        portfolioItemRepository.saveAndFlush(portfolioItem);

        int databaseSizeBeforeDelete = portfolioItemRepository.findAll().size();

        // Delete the portfolioItem
        restPortfolioItemMockMvc
            .perform(delete(ENTITY_API_URL_ID, portfolioItem.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PortfolioItem> portfolioItemList = portfolioItemRepository.findAll();
        assertThat(portfolioItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
