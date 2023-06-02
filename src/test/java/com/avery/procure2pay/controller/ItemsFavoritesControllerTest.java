package com.avery.procure2pay.controller;

import com.avery.procure2pay.model.ItemFavorites;
import com.avery.procure2pay.repository.ItemFavoritesRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
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

    @MockBean
    private ItemFavoritesRepository itemFavoritesRepository;


    // create standard item fovarites
    ItemFavorites FAVITEM_1 = new ItemFavorites(1L, "Tubing", "Long Heavy Tubing", 25.75, "each");
    ItemFavorites FAVITEM_2 = new ItemFavorites(2L, "Small Tubing", "Small Lite Tubing", 15.75, "pounds");
    ItemFavorites FAVITEM_3 = new ItemFavorites(3L, "XSmall Tubing", "Xtra-Small Heavy Tubing", 5.75, "skids");

    @Test
    @DisplayName("should return all itemFavorites and status 200")
    void getAllItemFavorites_success() throws Exception {
        // build data to retrieve
        List<ItemFavorites> itemFavoritesList = new ArrayList<>(Arrays.asList(FAVITEM_1, FAVITEM_2, FAVITEM_3));
        // itemfavorites repository should return three favitems
        when(itemFavoritesRepository.findAll()).thenReturn(itemFavoritesList);
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
        when(itemFavoritesRepository.findById(FAVITEM_2.getId())).thenReturn(Optional.of(FAVITEM_2));
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
}