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
            ItemFavorites FAVITEM_1 = new ItemFavorites("Tubing", "Long Heavy Tubing", 25.75, "Each");
            ItemFavorites FAVITEM_2 = new ItemFavorites("Pens", "Bright Alpha Soft Touch w/ Stylus", .88, "Each");
            ItemFavorites FAVITEM_3 = new ItemFavorites("Lay's Chips", "Kettle Cooked Variety Pack", 22.29, "Each");

            ItemFavorites FAVITEM_4 = new ItemFavorites("Tubing", "Long Heavy Tubing", 25.75, "Each");
            ItemFavorites FAVITEM_5 = new ItemFavorites("Dalworth Clean", "Carpet Cleaning Service", 55.00, "Each");
            ItemFavorites FAVITEM_6 = new ItemFavorites("Platinum Painting of Dallas", "Painting Service - Interior", 125.00, "Each");

            itemFavoritesRepository.save(FAVITEM_1);
            itemFavoritesRepository.save(FAVITEM_2);
            itemFavoritesRepository.save(FAVITEM_3);

            itemFavoritesRepository.save(FAVITEM_4);
            itemFavoritesRepository.save(FAVITEM_5);
            itemFavoritesRepository.save(FAVITEM_6);
        }
        logger.info("Count of seeded ItemFavorites records from ItemFavoritesDataLoader:  " + itemFavoritesRepository.count());
    }

}
