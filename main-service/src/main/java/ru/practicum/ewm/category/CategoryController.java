package ru.practicum.ewm.category;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.dto.NewCategoryDto;
import ru.practicum.ewm.category.service.CategoryService;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping(path = "admin/categories")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto createCategory(@Valid @RequestBody NewCategoryDto request) {
        log.info("Received request to create category: {}", request.getName());
        return categoryService.createCategory(request);
    }

    @DeleteMapping(path = "admin/categories/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@Positive @PathVariable long catId) {
        log.info("Received request to delete category with ID = {}", catId);
        categoryService.deleteCategory(catId);
    }

    @PatchMapping(path = "admin/categories/{catId}")
    public CategoryDto updateCategory(@Valid @RequestBody NewCategoryDto request,
                                      @Positive @PathVariable long catId) {
        log.info("Received request to update category with ID = {}", catId);
        return categoryService.updateCategory(request, catId);
    }

    @GetMapping(path = "categories/{catId}")
    public CategoryDto findCategoryById(@Positive @PathVariable long catId) {
        log.info("Received request to find category with ID = {}", catId);
        return categoryService.findCategoryById(catId);
    }
}
