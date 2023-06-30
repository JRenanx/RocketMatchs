package br.com.tier.rocketleaguematchs.service;

import java.util.List;

import br.com.tier.rocketleaguematchs.models.Country;


public interface CountryService {

    Country findById(Integer id);

    Country insert(Country country);

    Country update(Country country);

    void delete(Integer id);

    List<Country> listAll();

    List<Country> findAllByOrderByName();

    List<Country> findByNameStartsWithIgnoreCase(String name);


}
