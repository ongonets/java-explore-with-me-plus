package ru.practicum.ewm.category.service;

import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.dto.NewCategoryDto;

public interface CategoryService {

    CategoryDto createCategory(NewCategoryDto request);

    void deleteCategory(long id);

    CategoryDto findCategoryById(long id);
}