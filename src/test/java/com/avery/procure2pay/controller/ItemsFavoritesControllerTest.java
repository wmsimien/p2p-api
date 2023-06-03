package com.avery.procure2pay.controller;

import com.avery.procure2pay.model.ItemFavorites;
import com.avery.procure2pay.repository.ItemFavoritesRepository;
import com.avery.procure2pay.service.ItemFavoritesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;


@WebMvcTest(ItemsFavoritesController.class)
class ItemsFavoritesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    private ItemFavoritesRepository itemFavoritesRepository;

    @MockBean
    private ItemFavoritesService itemFavoritesService;


    // create standard item fovarites
    ItemFavorites FAVITEM_1 = new ItemFavorites(1L, "Tubing", "Long Heavy Tubing", 25.75, "each");
    ItemFavorites FAVITEM_2 = new ItemFavorites(2L, "Small Tubing", "Small Lite Tubing", 15.75, "pounds");
    ItemFavorites FAVITEM_3 = new ItemFavorites(3L, "XSmall Tubing", "Xtra-Small Heavy Tubing", 5.75, "skids");

    @Test
    @DisplayName("should return all itemFavorites and status 200")
    void getAllItemFavorites_success() throws Exception {
        // build data to retrieve
        List<ItemFavorites> itemFavoritesList = new ArrayList<>(Arrays.asList(FAVITEM_1, FAVITEM_2, FAVITEM_3));
        // itemfavorites service should return three favitems
        when(itemFavoritesService.getAllItemFavorites()).thenReturn(itemFavoritesList);
        // build endpoint which should return a json object
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/items/")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.data", hasSize(3)))
                        .andExpect(jsonPath("$.message").value("success"))
                        .andDo(print());


    }
    @Test
    @DisplayName("should return 200 a specific fav item based itemId")
    void getItemFavoritesById_success() throws Exception {
        // test repository
        when(itemFavoritesService.getItemFavoritesById(FAVITEM_2.getId())).thenReturn(Optional.of(FAVITEM_2));
        // test endpoint api/items/2/
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/items/{itemId}/", "2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(FAVITEM_2.getId()))
                .andExpect(jsonPath("$.data.name").value(FAVITEM_2.getName()))
                .andExpect(jsonPath("$.data.description").value(FAVITEM_2.getDescription()))
                .andExpect(jsonPath("$.data.unit_price").value(FAVITEM_2.getUnit_price()))
                .andExpect(jsonPath("$.data.uom").value(FAVITEM_2.getUom()))
                .andExpect(jsonPath("$.message").value("success"))
                .andDo(print());
    }

    @Test
    @DisplayName("return 201 when creating a new favorite item successfully")
    void createItemFavorites_success() throws Exception{
        // test repository
        when(itemFavoritesService.createItemFavorites(Mockito.any(ItemFavorites.class))).thenReturn(FAVITEM_3);
        // build mock endpoint to test endpoint
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/items/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(FAVITEM_3));
        // make/perform mock request
        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.data.id").value(FAVITEM_3.getId()))
                .andExpect(jsonPath("$.data.name").value(FAVITEM_3.getName()))
                .andExpect(jsonPath("$.data.description").value(FAVITEM_3.getDescription()))
                .andExpect(jsonPath("$.data.unit_price").value(FAVITEM_3.getUnit_price()))
                .andExpect(jsonPath("$.data.uom").value(FAVITEM_3.getUom()))
                .andExpect(jsonPath("$.message").value("success"))
                .andDo(print());
    }

//    @Test
//    @DisplayName("return 404 when not able to update favorite item")
//    void updateItemFavoritesById_recordNotFound() throws Exception{
//        // test repository
//        when(itemFavoritesRepository.save(Mockito.any(ItemFavorites.class))).thenReturn(FAVITEM_3);
//        // build mock endpoint to test endpoint
//        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/items/")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .content(this.mapper.writeValueAsString(FAVITEM_3));
//        // make/perform mock request
//        mockMvc.perform(mockRequest)
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$", notNullValue()))
//                .andExpect(jsonPath("$.data.id").value(FAVITEM_3.getId()))
//                .andExpect(jsonPath("$.data.name").value(FAVITEM_3.getName()))
//                .andExpect(jsonPath("$.data.description").value(FAVITEM_3.getDescription()))
//                .andExpect(jsonPath("$.data.unit_price").value(FAVITEM_3.getUnit_price()))
//                .andExpect(jsonPath("$.data.uom").value(FAVITEM_3.getUom()))
//                .andExpect(jsonPath("$.message").value("success"))
//                .andDo(print());
//    }
}