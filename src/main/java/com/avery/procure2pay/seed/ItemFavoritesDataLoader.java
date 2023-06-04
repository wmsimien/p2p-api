package com.avery.procure2pay.seed;

import com.avery.procure2pay.model.ItemFavorites;
import com.avery.procure2pay.repository.ItemFavoritesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class ItemFavoritesDataLoader implements CommandLineRunner {
    Logger logger = Logger.getLogger(ItemFavoritesDataLoader.class.getName());

    @Autowired
    ItemFavoritesRepository itemFavoritesRepository;

    @Override
    public void run(String... args) throws Exception {
        loadItemFavorites();
    }

    private void loadItemFavorites() {
        if (itemFavoritesRepository.count() == 0) {
            ItemFavorites FAVITEM_1 = new ItemFavorites("Tubing", "Long Heavy Tubing", 25.75, "each");
            ItemFavorites FAVITEM_2 = new ItemFavorites("Small Tubing", "Small Lite Tubing", 15.75, "pounds");
            ItemFavorites FAVITEM_3 = new ItemFavorites("XSmall Tubing", "Xtra-Small Heavy Tubing", 5.75, "skids");

            itemFavoritesRepository.save(FAVITEM_1);
            itemFavoritesRepository.save(FAVITEM_2);
            itemFavoritesRepository.save(FAVITEM_3);
        }
        logger.info("Count of seeded ItemFavorites records from ItemFavoritesDataLoader:  " + itemFavoritesRepository.count());
    }

}
