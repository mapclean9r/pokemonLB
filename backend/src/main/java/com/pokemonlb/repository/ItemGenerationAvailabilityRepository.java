package com.pokemonlb.repository;

import com.pokemonlb.model.dto.ItemSummaryDto;
import com.pokemonlb.model.entity.ItemGenerationAvailability;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ItemGenerationAvailabilityRepository
        extends JpaRepository<ItemGenerationAvailability, ItemGenerationAvailability.Key> {

    List<ItemGenerationAvailability> findByItemIdOrderByGeneration(Integer itemId);

    /** Every item once, by name. */
    @Query("""
            select distinct new com.pokemonlb.model.dto.ItemSummaryDto(i.itemId, i.itemName, i.category)
            from ItemGenerationAvailability i
            order by i.itemName""")
    List<ItemSummaryDto> findAllItems();

    /** Every item that exists in a generation, by name. */
    @Query("""
            select new com.pokemonlb.model.dto.ItemSummaryDto(i.itemId, i.itemName, i.category)
            from ItemGenerationAvailability i
            where i.generation = :generation
            order by i.itemName""")
    List<ItemSummaryDto> findItemsByGeneration(Short generation);
}
