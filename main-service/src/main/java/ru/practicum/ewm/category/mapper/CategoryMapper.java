package ru.practicum.ewm.category.mapper;

import org.mapstruct.Mapper;
import ru.practicum.ewm.category.Category;
import ru.practicum.ewm.category.CategoryDto;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto map(Category category);
}
