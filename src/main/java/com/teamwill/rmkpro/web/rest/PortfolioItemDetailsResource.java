package com.teamwill.rmkpro.web.rest;

import com.teamwill.rmkpro.domain.PortfolioItemDetails;
import com.teamwill.rmkpro.repository.PortfolioItemDetailsRepository;
import com.teamwill.rmkpro.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link PortfolioItemDetails}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PortfolioItemDetailsResource {

    private final Logger log = LoggerFactory.getLogger(PortfolioItemDetailsResource.class);

    private static final String ENTITY_NAME = "portfolioItemDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PortfolioItemDetailsRepository portfolioItemDetailsRepository;

    public PortfolioItemDetailsResource(PortfolioItemDetailsRepository portfolioItemDetailsRepository) {
        this.portfolioItemDetailsRepository = portfolioItemDetailsRepository;
    }

    /**
     * {@code POST  /portfolio-item-details} : Create a new portfolioItemDetails.
     *
     * @param portfolioItemDetails the portfolioItemDetails to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new portfolioItemDetails, or with status {@code 400 (Bad Request)} if the portfolioItemDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/portfolio-item-details")
    public ResponseEntity<PortfolioItemDetails> createPortfolioItemDetails(
        @RequestBody PortfolioItemDetails portfolioItemDetails
    ) throws URISyntaxException {
        log.debug("REST request to save PortfolioItemDetails : {}", portfolioItemDetails);
        if (portfolioItemDetails.getId() != null) {
            throw new BadRequestAlertException("A new portfolioItemDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PortfolioItemDetails result = portfolioItemDetailsRepository.save(portfolioItemDetails);
        return ResponseEntity
            .created(new URI("/api/portfolio-item-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /portfolio-item-details/:id} : Updates an existing portfolioItemDetails.
     *
     * @param id the id of the portfolioItemDetails to save.
     * @param portfolioItemDetails the portfolioItemDetails to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated portfolioItemDetails,
     * or with status {@code 400 (Bad Request)} if the portfolioItemDetails is not valid,
     * or with status {@code 500 (Internal Server Error)} if the portfolioItemDetails couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/portfolio-item-details/{id}")
    public ResponseEntity<PortfolioItemDetails> updatePortfolioItemDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PortfolioItemDetails portfolioItemDetails
    ) throws URISyntaxException {
        log.debug("REST request to update PortfolioItemDetails : {}, {}", id, portfolioItemDetails);
        if (portfolioItemDetails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, portfolioItemDetails.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!portfolioItemDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PortfolioItemDetails result = portfolioItemDetailsRepository.save(portfolioItemDetails);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, portfolioItemDetails.getId().toString())
            )
            .body(result);
    }

    /**
     * {@code PATCH  /portfolio-item-details/:id} : Partial updates given fields of an existing portfolioItemDetails, field will ignore if it is null
     *
     * @param id the id of the portfolioItemDetails to save.
     * @param portfolioItemDetails the portfolioItemDetails to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated portfolioItemDetails,
     * or with status {@code 400 (Bad Request)} if the portfolioItemDetails is not valid,
     * or with status {@code 404 (Not Found)} if the portfolioItemDetails is not found,
     * or with status {@code 500 (Internal Server Error)} if the portfolioItemDetails couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/portfolio-item-details/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PortfolioItemDetails> partialUpdatePortfolioItemDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PortfolioItemDetails portfolioItemDetails
    ) throws URISyntaxException {
        log.debug("REST request to partial update PortfolioItemDetails partially : {}, {}", id, portfolioItemDetails);
        if (portfolioItemDetails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, portfolioItemDetails.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!portfolioItemDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PortfolioItemDetails> result = portfolioItemDetailsRepository
            .findById(portfolioItemDetails.getId())
            .map(
                existingPortfolioItemDetails -> {
                    return existingPortfolioItemDetails;
                }
            )
            .map(portfolioItemDetailsRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, portfolioItemDetails.getId().toString())
        );
    }

    /**
     * {@code GET  /portfolio-item-details} : get all the portfolioItemDetails.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of portfolioItemDetails in body.
     */
    @GetMapping("/portfolio-item-details")
    public List<PortfolioItemDetails> getAllPortfolioItemDetails() {
        log.debug("REST request to get all PortfolioItemDetails");
        return portfolioItemDetailsRepository.findAll();
    }

    /**
     * {@code GET  /portfolio-item-details/:id} : get the "id" portfolioItemDetails.
     *
     * @param id the id of the portfolioItemDetails to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the portfolioItemDetails, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/portfolio-item-details/{id}")
    public ResponseEntity<PortfolioItemDetails> getPortfolioItemDetails(@PathVariable Long id) {
        log.debug("REST request to get PortfolioItemDetails : {}", id);
        Optional<PortfolioItemDetails> portfolioItemDetails = portfolioItemDetailsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(portfolioItemDetails);
    }

    /**
     * {@code DELETE  /portfolio-item-details/:id} : delete the "id" portfolioItemDetails.
     *
     * @param id the id of the portfolioItemDetails to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/portfolio-item-details/{id}")
    public ResponseEntity<Void> deletePortfolioItemDetails(@PathVariable Long id) {
        log.debug("REST request to delete PortfolioItemDetails : {}", id);
        portfolioItemDetailsRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
