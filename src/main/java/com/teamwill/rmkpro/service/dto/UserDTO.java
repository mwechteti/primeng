package com.teamwill.rmkpro.service.dto;

import com.teamwill.rmkpro.domain.User;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A DTO representing a user, with only the public attributes.
 */
public class UserDTO {

    private Long id;

    private String login;

    Set<PortfolioDTO> portfolios = new HashSet<>();

    public UserDTO() {
        // Empty constructor needed for Jackson.
    }

    public UserDTO(User user) {
        this.id = user.getId();
        // Customize it here if you need, or not, firstName/lastName/etc
        this.login = user.getLogin();
        if(CollectionUtils.isNotEmpty(user.getPortfolios())) {
            List<PortfolioDTO> listPortfoliosDTO = new ArrayList<>();
            user.getPortfolios().forEach(portfolio -> listPortfoliosDTO.add(new PortfolioDTO(portfolio)));
            this.portfolios = new HashSet<>(listPortfoliosDTO);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Set<PortfolioDTO> getPortfolios() {
        return portfolios;
    }

    public void setPortfolios(Set<PortfolioDTO> portfolios) {
        this.portfolios = portfolios;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserDTO{" +
            "id='" + id + '\'' +
            ", login='" + login + '\'' +
            "}";
    }
}
