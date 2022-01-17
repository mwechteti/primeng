package com.teamwill.rmkpro.web.rest;

import com.teamwill.rmkpro.IntegrationTest;
import com.teamwill.rmkpro.domain.PortfolioItemDetails;
import com.teamwill.rmkpro.repository.PortfolioItemDetailsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PortfolioItemDetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PortfolioItemDetailsResourceIT {

    private static final String ENTITY_API_URL = "/api/portfolio-item-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    @Autowired
    private PortfolioItemDetailsRepository portfolioItemDetailsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPortfolioItemDetailsMockMvc;

    private PortfolioItemDetails portfolioItemDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PortfolioItemDetails createEntity(EntityManager em) {
        PortfolioItemDetails portfolioItemDetails = new PortfolioItemDetails();
        return portfolioItemDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PortfolioItemDetails createUpdatedEntity(EntityManager em) {
        PortfolioItemDetails portfolioItemDetails = new PortfolioItemDetails();
        return portfolioItemDetails;
    }

    @BeforeEach
    public void initTest() {
        portfolioItemDetails = createEntity(em);
    }

    @Test
    @Transactional
    void createPortfolioItemDetails() throws Exception {
        int databaseSizeBeforeCreate = portfolioItemDetailsRepository.findAll().size();
        // Create the PortfolioItemDetails
        restPortfolioItemDetailsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(portfolioItemDetails))
            )
            .andExpect(status().isCreated());

        // Validate the PortfolioItemDetails in the database
        List<PortfolioItemDetails> portfolioItemDetailsList = portfolioItemDetailsRepository.findAll();
        assertThat(portfolioItemDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        PortfolioItemDetails testPortfolioItemDetails = portfolioItemDetailsList.get(
            portfolioItemDetailsList.size() - 1
        );
    }

    @Test
    @Transactional
    void createPortfolioItemDetailsWithExistingId() throws Exception {
        // Create the PortfolioItemDetails with an existing ID
        portfolioItemDetails.setId(1L);

        int databaseSizeBeforeCreate = portfolioItemDetailsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPortfolioItemDetailsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(portfolioItemDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the PortfolioItemDetails in the database
        List<PortfolioItemDetails> portfolioItemDetailsList = portfolioItemDetailsRepository.findAll();
        assertThat(portfolioItemDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPortfolioItemDetails() throws Exception {
        // Initialize the database
        portfolioItemDetailsRepository.saveAndFlush(portfolioItemDetails);

        // Get all the portfolioItemDetailsList
        restPortfolioItemDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(portfolioItemDetails.getId().intValue())));
    }

    @Test
    @Transactional
    void getPortfolioItemDetails() throws Exception {
        // Initialize the database
        portfolioItemDetailsRepository.saveAndFlush(portfolioItemDetails);

        // Get the portfolioItemDetails
        restPortfolioItemDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, portfolioItemDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(portfolioItemDetails.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingPortfolioItemDetails() throws Exception {
        // Get the portfolioItemDetails
        restPortfolioItemDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPortfolioItemDetails() throws Exception {
        // Initialize the database
        portfolioItemDetailsRepository.saveAndFlush(portfolioItemDetails);

        int databaseSizeBeforeUpdate = portfolioItemDetailsRepository.findAll().size();

        // Update the portfolioItemDetails
        PortfolioItemDetails updatedPortfolioItemDetails = portfolioItemDetailsRepository
            .findById(portfolioItemDetails.getId())
            .get();
        // Disconnect from session so that the updates on updatedPortfolioItemDetails are not directly saved in db
        em.detach(updatedPortfolioItemDetails);

        restPortfolioItemDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPortfolioItemDetails.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPortfolioItemDetails))
            )
            .andExpect(status().isOk());

        // Validate the PortfolioItemDetails in the database
        List<PortfolioItemDetails> portfolioItemDetailsList = portfolioItemDetailsRepository.findAll();
        assertThat(portfolioItemDetailsList).hasSize(databaseSizeBeforeUpdate);
        PortfolioItemDetails testPortfolioItemDetails = portfolioItemDetailsList.get(
            portfolioItemDetailsList.size() - 1
        );
    }

    @Test
    @Transactional
    void putNonExistingPortfolioItemDetails() throws Exception {
        int databaseSizeBeforeUpdate = portfolioItemDetailsRepository.findAll().size();
        portfolioItemDetails.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPortfolioItemDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, portfolioItemDetails.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(portfolioItemDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the PortfolioItemDetails in the database
        List<PortfolioItemDetails> portfolioItemDetailsList = portfolioItemDetailsRepository.findAll();
        assertThat(portfolioItemDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPortfolioItemDetails() throws Exception {
        int databaseSizeBeforeUpdate = portfolioItemDetailsRepository.findAll().size();
        portfolioItemDetails.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPortfolioItemDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(portfolioItemDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the PortfolioItemDetails in the database
        List<PortfolioItemDetails> portfolioItemDetailsList = portfolioItemDetailsRepository.findAll();
        assertThat(portfolioItemDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPortfolioItemDetails() throws Exception {
        int databaseSizeBeforeUpdate = portfolioItemDetailsRepository.findAll().size();
        portfolioItemDetails.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPortfolioItemDetailsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(portfolioItemDetails))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PortfolioItemDetails in the database
        List<PortfolioItemDetails> portfolioItemDetailsList = portfolioItemDetailsRepository.findAll();
        assertThat(portfolioItemDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePortfolioItemDetailsWithPatch() throws Exception {
        // Initialize the database
        portfolioItemDetailsRepository.saveAndFlush(portfolioItemDetails);

        int databaseSizeBeforeUpdate = portfolioItemDetailsRepository.findAll().size();

        // Update the portfolioItemDetails using partial update
        PortfolioItemDetails partialUpdatedPortfolioItemDetails = new PortfolioItemDetails();
        partialUpdatedPortfolioItemDetails.setId(portfolioItemDetails.getId());

        restPortfolioItemDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPortfolioItemDetails.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPortfolioItemDetails))
            )
            .andExpect(status().isOk());

        // Validate the PortfolioItemDetails in the database
        List<PortfolioItemDetails> portfolioItemDetailsList = portfolioItemDetailsRepository.findAll();
        assertThat(portfolioItemDetailsList).hasSize(databaseSizeBeforeUpdate);
        PortfolioItemDetails testPortfolioItemDetails = portfolioItemDetailsList.get(
            portfolioItemDetailsList.size() - 1
        );
    }

    @Test
    @Transactional
    void fullUpdatePortfolioItemDetailsWithPatch() throws Exception {
        // Initialize the database
        portfolioItemDetailsRepository.saveAndFlush(portfolioItemDetails);

        int databaseSizeBeforeUpdate = portfolioItemDetailsRepository.findAll().size();

        // Update the portfolioItemDetails using partial update
        PortfolioItemDetails partialUpdatedPortfolioItemDetails = new PortfolioItemDetails();
        partialUpdatedPortfolioItemDetails.setId(portfolioItemDetails.getId());

        restPortfolioItemDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPortfolioItemDetails.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPortfolioItemDetails))
            )
            .andExpect(status().isOk());

        // Validate the PortfolioItemDetails in the database
        List<PortfolioItemDetails> portfolioItemDetailsList = portfolioItemDetailsRepository.findAll();
        assertThat(portfolioItemDetailsList).hasSize(databaseSizeBeforeUpdate);
        PortfolioItemDetails testPortfolioItemDetails = portfolioItemDetailsList.get(
            portfolioItemDetailsList.size() - 1
        );
    }

    @Test
    @Transactional
    void patchNonExistingPortfolioItemDetails() throws Exception {
        int databaseSizeBeforeUpdate = portfolioItemDetailsRepository.findAll().size();
        portfolioItemDetails.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPortfolioItemDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, portfolioItemDetails.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(portfolioItemDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the PortfolioItemDetails in the database
        List<PortfolioItemDetails> portfolioItemDetailsList = portfolioItemDetailsRepository.findAll();
        assertThat(portfolioItemDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPortfolioItemDetails() throws Exception {
        int databaseSizeBeforeUpdate = portfolioItemDetailsRepository.findAll().size();
        portfolioItemDetails.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPortfolioItemDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(portfolioItemDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the PortfolioItemDetails in the database
        List<PortfolioItemDetails> portfolioItemDetailsList = portfolioItemDetailsRepository.findAll();
        assertThat(portfolioItemDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPortfolioItemDetails() throws Exception {
        int databaseSizeBeforeUpdate = portfolioItemDetailsRepository.findAll().size();
        portfolioItemDetails.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPortfolioItemDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(portfolioItemDetails))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PortfolioItemDetails in the database
        List<PortfolioItemDetails> portfolioItemDetailsList = portfolioItemDetailsRepository.findAll();
        assertThat(portfolioItemDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePortfolioItemDetails() throws Exception {
        // Initialize the database
        portfolioItemDetailsRepository.saveAndFlush(portfolioItemDetails);

        int databaseSizeBeforeDelete = portfolioItemDetailsRepository.findAll().size();

        // Delete the portfolioItemDetails
        restPortfolioItemDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, portfolioItemDetails.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PortfolioItemDetails> portfolioItemDetailsList = portfolioItemDetailsRepository.findAll();
        assertThat(portfolioItemDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
