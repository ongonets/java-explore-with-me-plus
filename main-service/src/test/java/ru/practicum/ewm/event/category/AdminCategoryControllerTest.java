package ru.practicum.ewm.event.category;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.ewm.category.AdminCategoryController;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.dto.NewCategoryDto;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.category.repository.CategoryRepository;
import ru.practicum.ewm.category.service.CategoryService;

import java.nio.charset.StandardCharsets;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AdminCategoryController.class)
public class AdminCategoryControllerTest {

    @Autowired
    ObjectMapper mapper;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CategoryService categoryService;

    @MockBean
    CategoryRepository categoryRepository;

    CategoryDto categoryDto = new CategoryDto(1L, "name");

    NewCategoryDto request = new NewCategoryDto("name");

    NewCategoryDto patchRequest = new NewCategoryDto("newCat");

    Category category = new Category(1L, "name");

    CategoryDto updatedCategoryDto = new CategoryDto(1L, "newCat");

    @Test
    void createCategory() throws Exception {
        when(categoryService.createCategory(any()))
                .thenReturn(categoryDto);

        mockMvc.perform(post("/admin/categories")
                        .content(mapper.writeValueAsString(request))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(categoryDto.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(categoryDto.getName())));
    }

    @Test
    void deleteCategory() throws Exception {
        mockMvc.perform(delete("/admin/categories/{catId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(categoryService, Mockito.times(1))
                .deleteCategory(1);
    }

    @Test
    void updateCategory() throws Exception {
        categoryRepository.save(category);
        when(categoryService.updateCategory(patchRequest, 1)).thenReturn(updatedCategoryDto);
        mockMvc.perform(patch("/admin/categories/{catId}", 1L)
                        .content(mapper.writeValueAsString(patchRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(updatedCategoryDto)));
    }
}
