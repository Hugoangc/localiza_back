package com.practice.localiza.repository;

import com.practice.localiza.entity.Brand;
import com.practice.localiza.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CarRepository extends JpaRepository<Car,Long> {
    public List<Car> findByBrand(Brand brand);
    List<Car> findAllByCarStatusTrue();
    public List<Car> findByName(String name);
    public List<Car> findByBrandName(String name);
//    @Query("SELECT c FROM Car c WHERE c.manufactureYear >= :manufactureYear")
    List<Car> findByNameContainingIgnoreCase(String name);

    @Query("FROM Car c WHERE c.manufactureYear >= :manufactureYear and c.carStatus = true")
    public List<Car> findByYearGte(Integer manufactureYear);

    List<Car> findByPriceBetween(Double minPrice, Double maxPrice);

    List<Car> findByBrandId(Long brandId);



}
