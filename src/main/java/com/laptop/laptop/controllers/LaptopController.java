package com.laptop.laptop.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.laptop.laptop.models.Laptop;
import com.laptop.laptop.services.laptop.LaptopService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/laptops")
public class LaptopController {

    private final LaptopService service;

    public LaptopController(LaptopService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Iterable<Laptop>> getAllLaptops(){
        return ResponseEntity.ok().body(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Laptop> getLaptopById(@PathVariable Long id){
        Optional<Laptop> laptop = service.findById(id);
        return laptop.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/page")
    public ResponseEntity<Iterable<Laptop>> getAllLaptopsByPage(Pageable pageable) {
        return ResponseEntity.ok().body(service.findAll(pageable));
    }

    @PostMapping
    public ResponseEntity<?> createLaptop(@Valid @RequestBody Laptop laptop, BindingResult result){
        if(result.hasErrors()){
            return validate(result);
        }
        Laptop laptopDb = service.save(laptop);
        return ResponseEntity.status(HttpStatus.CREATED).body(laptopDb);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateLaptop(@Valid @RequestBody Laptop laptop, @PathVariable Long id, BindingResult result){
        if(result.hasErrors()){
            return validate(result);
        }
        Laptop updatedLaptop = service.update(id, laptop);
        if (updatedLaptop == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedLaptop);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLaptopById(@PathVariable Long id){
        Optional<Laptop> laptop = service.findById(id);
        if(!laptop.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }

    private ResponseEntity<Map<String, Object>> validate(BindingResult result){
        Map<String, Object> errors = new HashMap<>();
        result.getFieldErrors().forEach(err -> 
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(errors);
    }
}
