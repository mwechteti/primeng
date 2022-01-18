package com.teamwill.rmkpro.web.rest;

import com.teamwill.rmkpro.domain.PortfolioItem;
import com.teamwill.rmkpro.repository.PortfolioItemRepository;
import com.teamwill.rmkpro.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.teamwill.rmkpro.domain.PortfolioItem}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PortfolioItemResource {

    private final Logger log = LoggerFactory.getLogger(PortfolioItemResource.class);

    private static final String ENTITY_NAME = "portfolioItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PortfolioItemRepository portfolioItemRepository;

    public PortfolioItemResource(PortfolioItemRepository portfolioItemRepository) {
        this.portfolioItemRepository = portfolioItemRepository;
    }

    /**
     * {@code POST  /portfolio-items} : Create a new portfolioItem.
     *
     * @param portfolioItem the portfolioItem to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new portfolioItem, or with status {@code 400 (Bad Request)} if the portfolioItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/portfolio-items")
    public ResponseEntity<PortfolioItem> createPortfolioItem(@RequestBody PortfolioItem portfolioItem) throws URISyntaxException {
        log.debug("REST request to save PortfolioItem : {}", portfolioItem);
        if (portfolioItem.getId() != null) {
            throw new BadRequestAlertException("A new portfolioItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PortfolioItem result = portfolioItemRepository.save(portfolioItem);
        return ResponseEntity
            .created(new URI("/api/portfolio-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /portfolio-items/:id} : Updates an existing portfolioItem.
     *
     * @param id the id of the portfolioItem to save.
     * @param portfolioItem the portfolioItem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated portfolioItem,
     * or with status {@code 400 (Bad Request)} if the portfolioItem is not valid,
     * or with status {@code 500 (Internal Server Error)} if the portfolioItem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/portfolio-items/{id}")
    public ResponseEntity<PortfolioItem> updatePortfolioItem(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PortfolioItem portfolioItem
    ) throws URISyntaxException {
        log.debug("REST request to update PortfolioItem : {}, {}", id, portfolioItem);
        if (portfolioItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, portfolioItem.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!portfolioItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PortfolioItem result = portfolioItemRepository.save(portfolioItem);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, portfolioItem.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /portfolio-items/:id} : Partial updates given fields of an existing portfolioItem, field will ignore if it is null
     *
     * @param id the id of the portfolioItem to save.
     * @param portfolioItem the portfolioItem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated portfolioItem,
     * or with status {@code 400 (Bad Request)} if the portfolioItem is not valid,
     * or with status {@code 404 (Not Found)} if the portfolioItem is not found,
     * or with status {@code 500 (Internal Server Error)} if the portfolioItem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/portfolio-items/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PortfolioItem> partialUpdatePortfolioItem(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PortfolioItem portfolioItem
    ) throws URISyntaxException {
        log.debug("REST request to partial update PortfolioItem partially : {}, {}", id, portfolioItem);
        if (portfolioItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, portfolioItem.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!portfolioItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PortfolioItem> result = portfolioItemRepository
            .findById(portfolioItem.getId())
            .map(
                existingPortfolioItem -> {
                    return existingPortfolioItem;
                }
            )
            .map(portfolioItemRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, portfolioItem.getId().toString())
        );
    }

    /**
     * {@code GET  /portfolio-items} : get all the portfolioItems.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of portfolioItems in body.
     */
    @GetMapping("/portfolio-items")
    public List<PortfolioItem> getPortfolioItems(@RequestParam(name = "portfolioId", required = false) Long portfolioId) {
        log.debug("REST request to get a list of PortfolioItems: {}", portfolioId);
        if(portfolioId != null) {
            return portfolioItemRepository.findByPortfolioId(portfolioId);
        }
        return portfolioItemRepository.findAll();
    }

    /**
     * {@code GET  /portfolio-items/:id} : get the "id" portfolioItem.
     *
     * @param id the id of the portfolioItem to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the portfolioItem, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/portfolio-items/{id}")
    public ResponseEntity<PortfolioItem> getPortfolioItem(@PathVariable Long id) {
        log.debug("REST request to get PortfolioItem : {}", id);
        Optional<PortfolioItem> portfolioItem = portfolioItemRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(portfolioItem);
    }

    /**
     * {@code DELETE  /portfolio-items/:id} : delete the "id" portfolioItem.
     *
     * @param id the id of the portfolioItem to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/portfolio-items/{id}")
    public ResponseEntity<Void> deletePortfolioItem(@PathVariable Long id) {
        log.debug("REST request to delete PortfolioItem : {}", id);
        portfolioItemRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
