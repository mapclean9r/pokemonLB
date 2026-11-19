package com.pokemonlb.service;

import com.pokemonlb.exception.NotFoundException;
import com.pokemonlb.model.dto.GameDto;
import com.pokemonlb.model.dto.ItemDetailDto;
import com.pokemonlb.model.dto.ItemSummaryDto;
import com.pokemonlb.model.entity.ItemGenerationAvailability;
import com.pokemonlb.repository.ItemGameAvailabilityRepository;
import com.pokemonlb.repository.ItemGenerationAvailabilityRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemGenerationAvailabilityRepository generationRepository;
    private final ItemGameAvailabilityRepository gameRepository;

    /** All items by name, optionally only those that exist in a generation. */
    public List<ItemSummaryDto> findAll(Short generation) {
        return generation == null
                ? generationRepository.findAllItems()
                : generationRepository.findItemsByGeneration(generation);
    }

    public ItemDetailDto findById(Integer id) {
        List<ItemGenerationAvailability> generations = generationRepository.findByItemIdOrderByGeneration(id);
        if (generations.isEmpty()) {
            throw new NotFoundException("Item", id);
        }
        ItemGenerationAvailability item = generations.getFirst();
        List<GameDto> games = gameRepository.findByItemIdOrderByGenerationAscGameIdAsc(id).stream()
                .map(g -> new GameDto(g.getGameId(), g.getGameName(), g.getGeneration()))
                .toList();
        return new ItemDetailDto(item.getItemId(), item.getItemName(), item.getCategory(),
                generations.stream().map(ItemGenerationAvailability::getGeneration).toList(), games);
    }
}
