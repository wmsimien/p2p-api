package com.avery.procure2pay.controller;


import com.avery.procure2pay.model.Employee;
import com.avery.procure2pay.model.ItemFavorites;
import com.avery.procure2pay.repository.ItemFavoritesRepository;
import com.avery.procure2pay.service.ItemFavoritesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * Method obtains a list of favorite items
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
     *
     * @param itemId
     * @return
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
    }

