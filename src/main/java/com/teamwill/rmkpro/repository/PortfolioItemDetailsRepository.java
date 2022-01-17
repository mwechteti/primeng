package com.teamwill.rmkpro.repository;

import com.teamwill.rmkpro.domain.PortfolioItemDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PortfolioItemDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PortfolioItemDetailsRepository extends JpaRepository<PortfolioItemDetails, Long> {}
