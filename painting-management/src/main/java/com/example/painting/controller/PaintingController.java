package com.example.painting.controller;

import com.example.painting.model.Category;
import com.example.painting.model.Painting;
import com.example.painting.model.PaintingFrom;
import com.example.painting.service.category.ICategoryService;
import com.example.painting.service.painting.IPaintingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/paintings")
public class PaintingController {
    @Value("${file-upload}")
    private String fileUpload;
    @Autowired
    private ICategoryService categoryRepo;
    @Autowired
    private IPaintingService paintingRepo;
    @Autowired
    private PaintingFrom paintingFr;

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
        modelAndView.addObject("paintings", new PaintingFrom());
        return modelAndView;
    }

    @PostMapping("/save")
    public ModelAndView createNewPainting(@Valid @ModelAttribute PaintingFrom paintingFrom, BindingResult bindingResult) {
        // Validate
        paintingFr.validate(paintingFrom, bindingResult);

        // Check for errors
        if (bindingResult.hasErrors()) {
            return new ModelAndView("/painting/create");
        }
        // Save image
        MultipartFile multipartFile = paintingFrom.getImage();
        String fileName = multipartFile.getOriginalFilename();
        try {
            FileCopyUtils.copy(paintingFrom.getImage().getBytes(), new File(fileUpload + fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Save painting
        Painting painting = new Painting(paintingFrom.getName(), paintingFrom.getHeight(), paintingFrom.getWidth(),
                paintingFrom.getMaterial(), paintingFrom.getDescription(), paintingFrom.getPrice(), fileName,
                paintingFrom.getCategory());
        paintingRepo.save(painting);

        // Return success message
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
