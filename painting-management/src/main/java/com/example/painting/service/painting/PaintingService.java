package com.example.painting.service.painting;

import com.example.painting.model.Category;
import com.example.painting.model.Painting;
import com.example.painting.repository.IPaintingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaintingService implements IPaintingService {
    @Autowired
    private IPaintingRepo paintingRepo;

    @Override
    public Iterable<Painting> findAll() {
        return paintingRepo.findAll();
    }

    @Override
    public Optional<Painting> findById(Long id) {
        return paintingRepo.findById(id);
    }

    @Override
    public void save(Painting painting) {
        paintingRepo.save(painting);
    }

    @Override
    public void delete(Long id) {
        paintingRepo.deleteById(id);
    }

    @Override
    public Iterable<Painting> findAllByCategory(Category category) {
        return paintingRepo.findAllByCategory(category);
    }

    @Override
    public Page<Painting> findAllByNameContaining(String name, Pageable pageable) {
        return paintingRepo.findAllByNameContaining(name,pageable);
    }


    @Override
    public Page<Painting> findAll(Pageable pageable) {
        return paintingRepo.findAll(pageable);
    }
}
