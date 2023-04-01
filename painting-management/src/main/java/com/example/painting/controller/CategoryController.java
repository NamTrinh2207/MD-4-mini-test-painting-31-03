package com.example.painting.controller;

import com.example.painting.model.Category;
import com.example.painting.service.category.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private ICategoryService categoryRepo;
    @GetMapping("")
    public ModelAndView list() {
        Iterable<Category> categories = categoryRepo.findAll();
        ModelAndView modelAndView = new ModelAndView("/category/homes");
        modelAndView.addObject("categories", categories);
        return modelAndView;
    }
}
