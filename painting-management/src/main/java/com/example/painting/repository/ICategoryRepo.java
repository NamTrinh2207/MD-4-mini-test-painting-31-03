package com.example.painting.repository;

import com.example.painting.model.Category;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryRepo extends PagingAndSortingRepository<Category, Long> {
}
