package com.avery.procure2pay.controller;


import com.avery.procure2pay.model.Employee;
import com.avery.procure2pay.model.ItemFavorites;
import com.avery.procure2pay.repository.ItemFavoritesRepository;
import com.avery.procure2pay.service.ItemFavoritesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path="/api")
public class ItemsFavoritesController {

    @Autowired
    ItemFavoritesRepository itemFavoritesRepository;
    @Autowired
    ItemFavoritesService itemFavoritesService;

    static HashMap<String, Object> message = new HashMap<>();


    /**
     * Endpoint call a method which obtains a list of favorite items
     * @return A response of 404 (NOT_FOUND) status when not able to return any favorite items and return a 200 (OK) status when favorite items are found.
     */
    @GetMapping(path="/items/")
    public ResponseEntity<?> getAllItemFavorites() {
        List<ItemFavorites> itemFavoritesList = itemFavoritesService.getAllItemFavorites();
        if (itemFavoritesList.isEmpty()) {
            message.put("message", "cannot find any item favorites");
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            message.put("message", "success");
            message.put("data", itemFavoritesList);
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }

    /**
     * Endpoint calls a method which obtains a favorite or most used item base on the provided id.
     * @param itemId Specific id of the favorite or most used it.
     * @return A response status of OK (200) if found or 404 (NOT_FOUND).
     */
    @GetMapping(path="/items/{itemId}")
    public ResponseEntity<?> getItemFavoritesById(@PathVariable(value="itemId") Long itemId) {
        Optional<ItemFavorites> favItem = itemFavoritesService.getItemFavoritesById(itemId);
        if (favItem.isPresent()) {
            message.put("message", "success");
            message.put("data", favItem.get());
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            message.put("message", "cannot find favorite item with id " + itemId);
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }

    /**
     *
     * @param itemFavoritesObject
     * @return
     */
    @PostMapping(path="/items/")
    public ResponseEntity<?> createItemFavorites(@RequestBody ItemFavorites itemFavoritesObject) {
        ItemFavorites itemFavorites = itemFavoritesRepository.save(itemFavoritesObject);
        if (itemFavorites == null) {
            message.put("message", "cannot find create item favorites");
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            message.put("message", "success");
            message.put("data", itemFavorites);
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        }
    }


}



