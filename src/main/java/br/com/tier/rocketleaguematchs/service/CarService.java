package br.com.tier.rocketleaguematchs.service;

import java.util.List;

import br.com.tier.rocketleaguematchs.models.Car;

public interface CarService {

    Car findById(Integer id);

    Car insert(Car car);

    Car update(Car car);

    void delete(Integer id);
    
    List<Car> listAll();
    
    List<Car> findByNameStartsWithIgnoreCase(String name);
}
