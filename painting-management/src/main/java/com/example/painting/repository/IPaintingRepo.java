package com.example.painting.repository;

import com.example.painting.model.Category;
import com.example.painting.model.Painting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPaintingRepo extends PagingAndSortingRepository<Painting, Long> {
    Iterable<Painting> findAllByCategory(Category category);

    Page<Painting> findAllByNameContaining(String name, Pageable pageable);
}
