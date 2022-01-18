package com.teamwill.rmkpro.web.rest;

import com.teamwill.rmkpro.domain.PortfolioItemStatus;
import com.teamwill.rmkpro.repository.PortfolioItemStatusRepository;
import com.teamwill.rmkpro.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link PortfolioItemStatus}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PortfolioItemStatusResource {

    private final Logger log = LoggerFactory.getLogger(PortfolioItemStatusResource.class);

    private static final String ENTITY_NAME = "portfolioItemStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PortfolioItemStatusRepository portfolioItemStatusRepository;

    public PortfolioItemStatusResource(PortfolioItemStatusRepository portfolioItemStatusRepository) {
        this.portfolioItemStatusRepository = portfolioItemStatusRepository;
    }

    /**
     * {@code POST  /portfolio-item-status} : Create a new portfolioItemStatus.
     *
     * @param portfolioItemStatus the portfolioItemStatus to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new portfolioItemStatus, or with status {@code 400 (Bad Request)} if the portfolioItemStatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/portfolio-item-status")
    public ResponseEntity<PortfolioItemStatus> createPortfolioItemStatus(@Valid @RequestBody PortfolioItemStatus portfolioItemStatus) throws URISyntaxException {
        log.debug("REST request to save PortfolioItemStatus : {}", portfolioItemStatus);
        if (portfolioItemStatus.getId() != null) {
            throw new BadRequestAlertException("A new portfolioItemStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PortfolioItemStatus result = portfolioItemStatusRepository.save(portfolioItemStatus);
        return ResponseEntity
            .created(new URI("/api/portfolio-item-status/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /portfolio-item-status/:id} : Updates an existing portfolioItemStatus.
     *
     * @param id the id of the portfolioItemStatus to save.
     * @param portfolioItemStatus the portfolioItemStatus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated portfolioItemStatus,
     * or with status {@code 400 (Bad Request)} if the portfolioItemStatus is not valid,
     * or with status {@code 500 (Internal Server Error)} if the portfolioItemStatus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/portfolio-item-status/{id}")
    public ResponseEntity<PortfolioItemStatus> updatePortfolioItemStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PortfolioItemStatus portfolioItemStatus
    ) throws URISyntaxException {
        log.debug("REST request to update PortfolioItemStatus : {}, {}", id, portfolioItemStatus);
        if (portfolioItemStatus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, portfolioItemStatus.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!portfolioItemStatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PortfolioItemStatus result = portfolioItemStatusRepository.save(portfolioItemStatus);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, portfolioItemStatus.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /portfolio-item-status} : get all the legalEntities.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of legalEntities in body.
     */
    @GetMapping("/portfolio-item-status")
    public List<PortfolioItemStatus> getAllPortfolioItemStatuses() {
        log.debug("REST request to get all PortfolioItemStatuses");
        return portfolioItemStatusRepository.findAll();
    }

    /**
     * {@code GET  /portfolio-item-status/:id} : get the "id" portfolioItemStatus.
     *
     * @param id the id of the portfolioItemStatus to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the portfolioItemStatus, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/portfolio-item-status/{id}")
    public ResponseEntity<PortfolioItemStatus> getPortfolioItemStatus(@PathVariable Long id) {
        log.debug("REST request to get PortfolioItemStatus : {}", id);
        Optional<PortfolioItemStatus> portfolioItemStatus = portfolioItemStatusRepository.findById(id.intValue());
        return ResponseUtil.wrapOrNotFound(portfolioItemStatus);
    }

    /**
     * {@code DELETE  /portfolio-item-status/:id} : delete the "id" portfolioItemStatus.
     *
     * @param id the id of the portfolioItemStatus to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/portfolio-item-status/{id}")
    public ResponseEntity<Void> deletePortfolioItemStatus(@PathVariable Long id) {
        log.debug("REST request to delete PortfolioItemStatus : {}", id);
        portfolioItemStatusRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
