package com.avery.procure2pay.repository;

import com.avery.procure2pay.model.ItemFavorites;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemFavoritesRepository extends JpaRepository<ItemFavorites, Long> {
}
