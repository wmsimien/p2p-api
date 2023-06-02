package com.avery.procure2pay.service;

import com.avery.procure2pay.repository.ItemFavoritesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemFavoritesService {

    private ItemFavoritesRepository itemFavoritesRepository;
    @Autowired
    public void setItemFavoritesRepository(ItemFavoritesRepository itemFavoritesRepository) {
        this.itemFavoritesRepository = itemFavoritesRepository;
    }


}
