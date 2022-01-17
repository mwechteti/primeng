package com.teamwill.rmkpro.repository;

import com.teamwill.rmkpro.domain.PortfolioItemStatusEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PortfolioItemStatusEntry entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PortfolioItemStatusEntryRepository extends JpaRepository<PortfolioItemStatusEntry, Long> {}
