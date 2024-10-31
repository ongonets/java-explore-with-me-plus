package ru.practicum.ewm.event.category;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.ewm.category.PublicCategoryController;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.dto.NewCategoryDto;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.category.repository.CategoryRepository;
import ru.practicum.ewm.category.service.CategoryService;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PublicCategoryController.class)
public class PublicCategoryControllerTest {

    @Autowired
    ObjectMapper mapper;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CategoryService categoryService;

    @MockBean
    CategoryRepository categoryRepository;

    CategoryDto categoryDto = new CategoryDto(1L, "name");

    Category category = new Category(1L, "name");

    List<CategoryDto> categoryDtos = List.of(categoryDto);

    @Test
    void findCategoryById() throws Exception {
        when(categoryService.findCategoryById(1L)).thenReturn(categoryDto);
        mockMvc.perform(get("/categories/{catId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("name"));
        verify(categoryService, times(1)).findCategoryById(1L);
    }

    @Test
    void findCategories() throws Exception {
        categoryRepository.save(category);
        when(categoryService.findCategories(0, 1)).thenReturn(categoryDtos);
        mockMvc.perform(get("/categories?from=0&size=1"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(categoryDtos)));
    }
}
