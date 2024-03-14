package com.laptop.laptop.services.laptop;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.laptop.laptop.models.Laptop;

import java.util.Optional;

@Service
public interface LaptopService {

    Iterable<Laptop> findAll();

    Page<Laptop> findAll(Pageable pageable);

    Optional<Laptop> findById(Long id);

    Laptop save(Laptop laptop);

    Laptop update(Long id, Laptop laptopDetails);

    void deleteById(Long id);
   
}