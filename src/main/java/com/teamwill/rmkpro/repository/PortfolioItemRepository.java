package com.teamwill.rmkpro.repository;

import com.teamwill.rmkpro.domain.PortfolioItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the PortfolioItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PortfolioItemRepository extends JpaRepository<PortfolioItem, Long> {

    List<PortfolioItem> findByPortfolioId(Long portfolioId);

}
