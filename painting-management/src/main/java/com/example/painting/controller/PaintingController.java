package com.example.painting.controller;

import com.example.painting.model.Category;
import com.example.painting.model.Painting;
import com.example.painting.service.category.ICategoryService;
import com.example.painting.service.painting.IPaintingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/paintings")
public class PaintingController {
    @Autowired
    private ICategoryService categoryRepo;
    @Autowired
    private IPaintingService paintingRepo;

    @ModelAttribute("categories")
    public Iterable<Category> categories() {
        return categoryRepo.findAll();
    }

    // Hien thi va tim kiem
    @GetMapping("")
    public ModelAndView list(@RequestParam("search") Optional<String> search,
                             @RequestParam("search2") Optional<Category> category,
                             Pageable pageable) {
        Page<Painting> paintings;
        if (search.isPresent() || category.isPresent()) {
            paintings = paintingRepo.findAllByNameContainingAndCategory(search.get(), category.get(), pageable);
        } else {
            paintings = paintingRepo.findAll(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("/painting/homes");
        modelAndView.addObject("paintings", paintings);
        return modelAndView;
    }

    //Tao moi tranh
    @GetMapping("/create")
    public ModelAndView showCreateFrom() {
        ModelAndView modelAndView = new ModelAndView("/painting/create");
        modelAndView.addObject("paintings", new Painting());
        return modelAndView;
    }

    @PostMapping("/save")
    public ModelAndView createNewPainting(@ModelAttribute Painting painting) {
        paintingRepo.save(painting);
        ModelAndView modelAndView = new ModelAndView("/painting/create");
        modelAndView.addObject("paintings", new Painting());
        modelAndView.addObject("mess", "create a new painting successfully");
        return modelAndView;
    }

    //xoa tranh
    @GetMapping("/delete/{id}")
    public ModelAndView showDeleteFrom(@PathVariable Long id) {
        Optional<Painting> painting = paintingRepo.findById(id);
        if (painting.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("/painting/delete");
            modelAndView.addObject("paintings", painting.get());
            return modelAndView;
        } else {
            return new ModelAndView("/error.404");
        }
    }

    @PostMapping("/remove")
    public ModelAndView delete(@ModelAttribute Painting painting) {
        paintingRepo.delete(painting.getId());
        return new ModelAndView("redirect:/paintings");
    }

    //sửa thông tin
    @GetMapping("/update/{id}")
    public ModelAndView showUpdateFrom(@PathVariable Long id) {
        Optional<Painting> painting = paintingRepo.findById(id);
        if (painting.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("/painting/update");
            modelAndView.addObject("paintings", painting.get());
            return modelAndView;
        } else {
            return new ModelAndView("/error.404");
        }
    }

    @PostMapping("/edit")
    public ModelAndView update(@ModelAttribute Painting painting) {
        paintingRepo.save(painting);
        return new ModelAndView("redirect:/paintings");
    }
}
