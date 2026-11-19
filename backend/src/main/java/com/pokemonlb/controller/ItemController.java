package com.pokemonlb.controller;

import com.pokemonlb.config.ApiUrls;
import com.pokemonlb.model.dto.ItemDetailDto;
import com.pokemonlb.model.dto.ItemSummaryDto;
import com.pokemonlb.service.ItemService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiUrls.Items.BASE)
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    /** GET /api/v1/items?generation=9 (generation optional) */
    @GetMapping
    public List<ItemSummaryDto> findAll(@RequestParam(required = false) Short generation) {
        return itemService.findAll(generation);
    }

    /** GET /api/v1/items/1 */
    @GetMapping(ApiUrls.Items.BY_ID)
    public ItemDetailDto findById(@PathVariable Integer id) {
        return itemService.findById(id);
    }
}
