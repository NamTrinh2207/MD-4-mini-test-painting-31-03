package com.example.painting.model;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;


@Component
public class PaintingFrom implements Validator {
    private Long id;
    private String name;
    private Long height;
    private Long width;
    private String material;
    private String description;
    private Long price;
    private MultipartFile image;
    private Category category;

    public PaintingFrom() {
    }

    public PaintingFrom(String name, Long height, Long width, String material, String description, Long price, MultipartFile image, Category category) {
        this.name = name;
        this.height = height;
        this.width = width;
        this.material = material;
        this.description = description;
        this.price = price;
        this.image = image;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getHeight() {
        return height;
    }

    public void setHeight(Long height) {
        this.height = height;
    }

    public Long getWidth() {
        return width;
    }

    public void setWidth(Long width) {
        this.width = width;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    @Override
    public boolean supports(Class<?> clazz) {
        return PaintingFrom.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PaintingFrom paintingFrom = (PaintingFrom) target;

        // validate name
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty");
        if (paintingFrom.getName().length() < 2 || paintingFrom.getName().length() > 50) {
            errors.rejectValue("name", "Size.paintingForm.name");
        }

        // validate height
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "height", "NotEmpty");
        if (paintingFrom.getHeight() <= 0) {
            errors.rejectValue("height", "Positive.paintingForm.height");
        }

        // validate width
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "width", "NotEmpty");
        if (paintingFrom.getWidth() <= 0) {
            errors.rejectValue("width", "Positive.paintingForm.width");
        }

        // validate material
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "material", "NotEmpty");
        if (paintingFrom.getMaterial().length() < 2 || paintingFrom.getMaterial().length() > 50) {
            errors.rejectValue("material", "Size.paintingForm.material");
        }

        // validate description
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "NotEmpty");
        if (paintingFrom.getDescription().length() < 2 || paintingFrom.getDescription().length() > 500) {
            errors.rejectValue("description", "Size.paintingForm.description");
        }

        // validate price
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price", "NotEmpty");
        if (paintingFrom.getPrice() <= 0) {
            errors.rejectValue("price", "Positive.paintingForm.price");
        }

        // validate category
        if (paintingFrom.getCategory() == null || paintingFrom.getCategory().getId() == null) {
            errors.rejectValue("category", "NotEmpty");
        }

        // validate image
        if (paintingFrom.getImage() == null || paintingFrom.getImage().isEmpty()) {
            errors.rejectValue("image", "NotEmpty");
        } else {
            String contentType = paintingFrom.getImage().getContentType();
            if (!contentType.equals("image/jpeg") && !contentType.equals("image/png")) {
                errors.rejectValue("image", "Type.paintingForm.image");
            }
        }
    }
}
