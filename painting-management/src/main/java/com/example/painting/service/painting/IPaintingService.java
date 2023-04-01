package com.example.painting.service.painting;

import com.example.painting.model.Category;
import com.example.painting.model.Painting;
import com.example.painting.service.IGenericService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IPaintingService extends IGenericService<Painting> {
    Iterable<Painting> findAllByCategory(Category category);
    Page<Painting> findAllByNameContaining(String name, Pageable pageable);
    Page<Painting> findAll(Pageable pageable);
}
