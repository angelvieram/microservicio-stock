package com.bootcamppragma.microserviciostock.adapters.driven.http.controller;

import com.bootcamppragma.microserviciostock.adapters.driven.http.dto.request.AddCategoryRequest;
import com.bootcamppragma.microserviciostock.adapters.driven.http.dto.response.CategoryResponse;
import com.bootcamppragma.microserviciostock.adapters.driven.http.mapper.ICategoryRequestMapper;
import com.bootcamppragma.microserviciostock.adapters.driven.http.mapper.ICategoryResponseMapper;
import com.bootcamppragma.microserviciostock.domain.api.ICategoryServicePort;
import com.bootcamppragma.microserviciostock.domain.model.Category;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebMvcTest(CategoryRestControllerAdapter.class)
class CategoryRestControllerAdapterTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ICategoryServicePort categoryServicePort;

    @MockBean
    private ICategoryRequestMapper categoryRequestMapper;

    @MockBean
    private ICategoryResponseMapper categoryResponseMapper;

    @Test
    @DisplayName("POST /api/categorias should create a category and return status 201")
    void addCategory() throws Exception {
        // GIVEN
        AddCategoryRequest request = new AddCategoryRequest("Electronics", "Devices and gadgets");

        // Mock behavior for service layer
        when(categoryRequestMapper.addRequestToCategory(any(AddCategoryRequest.class)))
                .thenReturn(new Category(1L, "Electronics", "Devices and gadgets"));

        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.post("/api/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        // THEN
        verify(categoryServicePort, times(1)).saveCategory(any(Category.class));
    }

    @Test
    @DisplayName("GET /api/categorias/{categoryName} should return category and status 200")
    void getCategory() throws Exception {
        // GIVEN
        String categoryName = "Electronics";
        CategoryResponse response = new CategoryResponse(1L, categoryName, "Devices and gadgets");

        // Mock behavior for service layer
        when(categoryServicePort.getCategory(anyString())).thenReturn(new Category(1L, categoryName, "Devices and gadgets"));
        when(categoryResponseMapper.toCategoryResponse(any(Category.class))).thenReturn(response);

        // WHEN & THEN
        mockMvc.perform(MockMvcRequestBuilders.get("/api/categorias/{categoryName}", categoryName)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(categoryName))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Devices and gadgets"));

        verify(categoryServicePort, times(1)).getCategory(categoryName);
    }

    private String asJsonString(Object obj) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }
}

