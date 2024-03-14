package com.laptop.laptop.services.laptop;

import com.laptop.laptop.models.Laptop;
import com.laptop.laptop.repository.LaptopRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LaptopServiceImplements implements LaptopService {

    private final LaptopRepository repository;

    public LaptopServiceImplements(LaptopRepository repository) {
        this.repository = repository;
    }

    @Override
    public Iterable<Laptop> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<Laptop> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Optional<Laptop> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Laptop save(Laptop laptop) {
        return repository.save(laptop);
    }

    @Override
    public Laptop update(Long id, Laptop laptopDetails) {
        return repository.findById(id)
                .map(laptop -> {
                    laptop.setMarca(laptopDetails.getMarca());
                    laptop.setModelo(laptopDetails.getModelo());
                    laptop.setSerial(laptopDetails.getSerial());
                    laptop.setProcesador(laptopDetails.getProcesador());
                    laptop.setRam(laptopDetails.getRam());
                    laptop.setAlmacenamiento(laptopDetails.getAlmacenamiento());
                    return repository.save(laptop);
                }).orElseGet(() -> {
                    laptopDetails.setIdLaptop(id);
                    return repository.save(laptopDetails);
                });
    }
    

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

}