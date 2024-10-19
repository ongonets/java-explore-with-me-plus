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
import ru.practicum.ewm.errorHandler.exception.ConflictDataException;
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
        checkForCategoryDuplicates(category.getName());
        category = categoryRepository.save(category);
        log.info("Category is saved: {}", category.getName());
        return categoryMapper.map(category);
    }

    @Override
    public void deleteCategory(long id) {
        Category categoryToDelete = findCategory(id);
        categoryRepository.delete(categoryToDelete);
        log.info("Category with ID = {} is deleted", id);
    }

    @Override
    public CategoryDto updateCategory(NewCategoryDto request, long id) {
        Category category = findCategory(id);
        checkForCategoryDuplicates(request.getName());
        category.setName(request.getName());
        categoryRepository.save(category);
        log.info("Category with ID = {} is updated", id);
        return categoryMapper.map(category);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDto findCategoryById(long id) {
        Category category = findCategory(id);
        log.info("Category with ID = {} is found: {}", id, category.getName());
        return categoryMapper.map(category);
    }

    private Category findCategory(long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Category with ID = {} is not found ", id);
                    return new NotFoundException("Not found category with ID = " + id);
                });
    }

    private void checkForCategoryDuplicates(String categoryName) {
        boolean isDuplicate = categoryRepository.existsByNameIgnoreCase(categoryName);
        if (isDuplicate) {
            log.error("Category with name: {} already exists", categoryName);
            throw new ConflictDataException("This category already exists");
        }
    }

}
