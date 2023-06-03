package com.avery.procure2pay.service;

import com.avery.procure2pay.model.ItemFavorites;
import com.avery.procure2pay.model.Supplier;
import com.avery.procure2pay.repository.ItemFavoritesRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class ItemFavoritesServiceTest {

    @MockBean
    ItemFavoritesRepository itemFavoritesRepository;

    @Autowired
    ItemFavoritesService itemFavoritesService;

    // create standard items
    ItemFavorites FAVITEM_1 = new ItemFavorites(1L, "Tubing", "Long Heavy Tubing", 25.75, "each");
    ItemFavorites FAVITEM_2 = new ItemFavorites(2L, "Small Tubing", "Small Lite Tubing", 15.75, "pounds");

    @Test
    void getAllItemFavorites() {
        List<ItemFavorites> itemFavoritesLists = new ArrayList<>(Arrays.asList(FAVITEM_1, FAVITEM_2));
        // mock itemsFavoriteRepository findAll()
        when(itemFavoritesRepository.findAll()).thenReturn(itemFavoritesLists);

        List<ItemFavorites> itemFavoritesList = itemFavoritesService.getAllItemFavorites();
        Assertions.assertEquals(2, itemFavoritesList.size());
    }

    @Test
    void getItemFavoritesById() {
    }

    @Test
    void createItemFavorites() {
    }

    @Test
    void updateItemFavoritesById() {
    }

    @Test
    void deleteItemFavorites() {
    }
}