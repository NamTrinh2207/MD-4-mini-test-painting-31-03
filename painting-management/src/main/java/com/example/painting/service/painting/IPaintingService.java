package com.example.painting.service.painting;

import com.example.painting.model.Category;
import com.example.painting.model.Painting;
import com.example.painting.service.IGenericService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IPaintingService extends IGenericService<Painting> {
    Page<Painting> findAllByCategory(Category category, Pageable pageable);

    Page<Painting> findAllByNameContainingAndCategory(String name, Category category, Pageable pageable);

    Page<Painting> findAll(Pageable pageable);
}
