package com.avery.procure2pay.controller;


import com.avery.procure2pay.exception.InformationNotFoundException;
import com.avery.procure2pay.model.ItemFavorites;
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
    ItemFavoritesService itemFavoritesService;

    static HashMap<String, Object> message = new HashMap<>();


    /**
     * Endpoint call a method which obtains a list of favorite items
     * @return A response of 404 (NOT_FOUND) status when not able to return any favorite items and return a 200 (OK) status when favorite items are found.
     */
    @GetMapping(path="/items/")
    public ResponseEntity<?> getAllItemFavorites() throws InformationNotFoundException {
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
    @GetMapping(path="/items/{itemId}/")
    public ResponseEntity<?> getItemFavoritesById(@PathVariable(value="itemId") Long itemId) throws InformationNotFoundException {
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
     * Endpoint calls method to create a new favorite item record.
     * @param itemFavoritesObject Favorite item data to create new item record.
     * @return Response stat us of 201 (CREATED) when successfully created and status of 404 when not able to create
     */
    @PostMapping(path="/items/")
    public ResponseEntity<?> createItemFavorites(@RequestBody ItemFavorites itemFavoritesObject) throws InformationNotFoundException {
        ItemFavorites itemFavorites = itemFavoritesService.createItemFavorites(itemFavoritesObject);
        if (itemFavorites == null) {
            message.put("message", "cannot find create item favorite");
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            message.put("message", "success");
            message.put("data", itemFavorites);
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        }
    }

    /**
     * Endpoint calls method to update a specific favorite item based on the item id with the supplied data elements.
     * @param itemId Specific id of the favorite item to update.
     * @param itemFavoritesObject Favorite item data elements to update favorite id with/
     * @return Response of 200 (OK) when the specific item is updated and 404 (NOT_FOUND) when not able to.
     * @throws InformationNotFoundException Response message.
     */
    @PutMapping(path="/items/{itemId}/")
    public ResponseEntity<?> updateItemFavoritesById(@PathVariable(value="itemId") Long itemId, @RequestBody ItemFavorites itemFavoritesObject) throws InformationNotFoundException {
        Optional<ItemFavorites> favItemToUpdate = itemFavoritesService.updateItemFavoritesById(itemId, itemFavoritesObject);
        if (favItemToUpdate.isEmpty()) {
            message.put("message", "cannot find favorite item with id " + itemId);
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            message.put("message", "item favorite with id " + itemId + " has been successfully updated");
            message.put("data", favItemToUpdate.get());
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }

    /**
     * Method calls service to delete or remove specific favitem
     * @param itemId Specific id to delete/remove.
     * @return Response status 404 when specified item id is not found and status of 200 when favitem is deleted successfully.
     */
    @DeleteMapping(path="/items/{itemId}/")
    public ResponseEntity<?> deleteItemFavoritesById(@PathVariable(value="itemId") Long itemId) {
        Optional<ItemFavorites> favItemToDelete = itemFavoritesService.deleteItemFavorites(itemId);
        if (favItemToDelete.isEmpty()) {
            message.put("message", "cannot find favorite item with id " + itemId);
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            message.put("message", "item favorite with id " + itemId + " has been successfully deleted");
            message.put("data", favItemToDelete.get());
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }
}



