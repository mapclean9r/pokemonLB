package com.pokemonlb.repository;

import com.pokemonlb.model.entity.ItemGameAvailability;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemGameAvailabilityRepository
        extends JpaRepository<ItemGameAvailability, ItemGameAvailability.Key> {

    List<ItemGameAvailability> findByItemIdOrderByGenerationAscGameIdAsc(Integer itemId);

    List<ItemGameAvailability> findByGameIdOrderByItemName(Integer gameId);
}
