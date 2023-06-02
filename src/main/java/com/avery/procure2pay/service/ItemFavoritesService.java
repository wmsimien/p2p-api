package com.avery.procure2pay.service;

import com.avery.procure2pay.model.ItemFavorites;
import com.avery.procure2pay.repository.ItemFavoritesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
public class ItemFavoritesService {

    private ItemFavoritesRepository itemFavoritesRepository;
    @Autowired
    public void setItemFavoritesRepository(ItemFavoritesRepository itemFavoritesRepository) {
        this.itemFavoritesRepository = itemFavoritesRepository;
    }

    /**
     * Method obtains a list of favorite or most used items.
     * @return List of items.
     */
    public List<ItemFavorites> getAllItemFavorites() {
        return itemFavoritesRepository.findAll();
    }

    /**
     * Obtains a favorite item based on item id.
     * @param itemId The specific favorite item.
     * @return Specified item.
     */
    public Optional<ItemFavorites> getItemFavoritesById(Long itemId) {
        return itemFavoritesRepository.findById(itemId);
    }


}



