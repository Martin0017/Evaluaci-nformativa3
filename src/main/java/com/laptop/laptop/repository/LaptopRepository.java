package com.laptop.laptop.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.laptop.laptop.models.Laptop;

public interface LaptopRepository extends PagingAndSortingRepository<Laptop, Long>, CrudRepository<Laptop, Long> {
}