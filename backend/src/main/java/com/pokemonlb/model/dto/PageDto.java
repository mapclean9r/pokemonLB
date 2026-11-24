package com.pokemonlb.model.dto;

import java.util.List;
import org.springframework.data.domain.Page;

/**
 * One page of a longer list. page is zero-based; hasNext tells a client whether to ask for page + 1.
 */
public record PageDto<T>(List<T> items, int page, int size, long totalItems, int totalPages, boolean hasNext) {

    public static <T> PageDto<T> from(Page<?> page, List<T> items) {
        return new PageDto<>(items, page.getNumber(), page.getSize(), page.getTotalElements(), page.getTotalPages(),
                page.hasNext());
    }
}
