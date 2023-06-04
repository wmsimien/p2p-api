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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@WebMvcTest(ItemsFavoritesController.class)
class ItemsFavoritesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

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
        // test service
        when(itemFavoritesService.getItemFavoritesById(FAVITEM_2.getId())).thenReturn(Optional.of(FAVITEM_2));
        // test endpoint api/items/2/
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/items/{itemId}/", 2L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(FAVITEM_2.getId()))
                .andExpect(jsonPath("$.data.name").value(FAVITEM_2.getName()))
                .andExpect(jsonPath("$.data.description").value(FAVITEM_2.getDescription()))
                .andExpect(jsonPath("$.data.unitPrice").value(FAVITEM_2.getUnitPrice()))
                .andExpect(jsonPath("$.data.uom").value(FAVITEM_2.getUom()))
                .andExpect(jsonPath("$.message").value("success"))
                .andDo(print());
    }

    @Test
    @DisplayName("return 201 when creating a new favorite item successfully")
    void createItemFavorites_success() throws Exception{
        // test service
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
                .andExpect(jsonPath("$.data.unitPrice").value(FAVITEM_3.getUnitPrice()))
                .andExpect(jsonPath("$.data.uom").value(FAVITEM_3.getUom()))
                .andExpect(jsonPath("$.message").value("success"))
                .andDo(print());
    }

    @Test
    @DisplayName("return 404 when not able to update favorite item")
    void updateItemFavoritesById_recordNotFound() throws Exception{
        // create a new favitem
        ItemFavorites newItemFavorites = new ItemFavorites("New Tubing", "New Long Heavy Tubing", 25.75, "each");
        // test service
        when(itemFavoritesService.updateItemFavoritesById(anyLong(), Mockito.any(ItemFavorites.class))).thenReturn(Optional.empty());
        // build mock request to test endpoint api/items/1/
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/api/items/{itemId}/", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        // make mock request
        mockMvc.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.message").value("cannot find favorite item with id 1"))
                .andDo(print());
    }

    @Test
    @DisplayName("return 200 when able to update favorite item")
    void updateItemFavoritesById_success() throws Exception{
        // create new favitems
        ItemFavorites newItemFavorites = new ItemFavorites("New Tubing", "New Long Heavy Tubing", 25.75, "each");
        ItemFavorites updatedItemFavorites = new ItemFavorites("Updated New Tubing", "Updated New Long Heavy Tubing", 125.75, "dozen");
        // test service
        when(itemFavoritesService.updateItemFavoritesById(anyLong(), Mockito.any(ItemFavorites.class))).thenReturn(Optional.of(updatedItemFavorites));
        // test endpoint api/items/1/
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put("/api/items/{itemId}/", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(newItemFavorites));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.data.id").value(updatedItemFavorites.getId()))
                .andExpect(jsonPath("$.data.name").value(updatedItemFavorites.getName()))
                .andExpect(jsonPath("$.data.description").value(updatedItemFavorites.getDescription()))
                .andExpect(jsonPath("$.data.unitPrice").value(updatedItemFavorites.getUnitPrice()))
                .andExpect(jsonPath("$.data.uom").value(updatedItemFavorites.getUom()))
                .andExpect(jsonPath("$.message").value("item favorite with id 1 has been successfully updated"))
                .andDo(print());
    }

    @Test
    @DisplayName("return 404 when not able to delete favitem")
    void deleteItemFavoritesById_recordNotFound() throws Exception {
        // test service
        when(itemFavoritesService.deleteItemFavorites(FAVITEM_2.getId())).thenReturn(Optional.empty());
        // test endpoint api/items/2/
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/api/items/{itemId}/", 2L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("cannot find favorite item with id 2"))
                .andDo(print());
    }

    @Test
    @DisplayName("return 200 when able to delete favitem")
    void deleteItemFavoritesById_success() throws Exception {
        // test service
        when(itemFavoritesService.deleteItemFavorites(FAVITEM_3.getId())).thenReturn(Optional.of(FAVITEM_3));
        // test endpoint api/items/3/
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/api/items/{itemId}/", 3L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.data.id").value(FAVITEM_3.getId()))
                .andExpect(jsonPath("$.data.name").value(FAVITEM_3.getName()))
                .andExpect(jsonPath("$.data.description").value(FAVITEM_3.getDescription()))
                .andExpect(jsonPath("$.data.unitPrice").value(FAVITEM_3.getUnitPrice()))
                .andExpect(jsonPath("$.data.uom").value(FAVITEM_3.getUom()))
                .andExpect(jsonPath("$.message").value("item favorite with id 3 has been successfully deleted"))
                .andDo(print());
    }
}