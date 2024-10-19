package ru.practicum.ewm.category.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.dto.NewCategoryDto;
import ru.practicum.ewm.category.mapper.CategoryMapper;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.category.repository.CategoryRepository;
import ru.practicum.ewm.errorHandler.exception.NotFoundException;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {


    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDto createCategory(NewCategoryDto request) {
        Category category = categoryMapper.mapToCategory(request);
        category = categoryRepository.save(category);
        log.info("Category is saved: {}", category.getName());
        return categoryMapper.map(category);
    }

    @Override
    public void deleteCategory(long id) {
        Category categoryToDelete = categoryRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Not found category with ID = {}", id);
                    return new NotFoundException("Not found category with ID = " + id);
                });
        categoryRepository.delete(categoryToDelete);
        log.info("Category with ID = {} is deleted", id);
    }

    @Override
    public CategoryDto findCategoryById(long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Category with ID = {} is not found ", id);
                    return new NotFoundException("Not found category with ID = " + id);
                });
        log.info("Category with ID = {} is found: {}", id, category.getName());
        return categoryMapper.map(category);
    }
}
