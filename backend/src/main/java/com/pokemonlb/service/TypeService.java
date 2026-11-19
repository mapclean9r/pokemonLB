package com.pokemonlb.service;

import com.pokemonlb.exception.NotFoundException;
import com.pokemonlb.model.dto.PokemonSummaryDto;
import com.pokemonlb.model.dto.TypeDto;
import com.pokemonlb.repository.PokemonRepository;
import com.pokemonlb.repository.TypeRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TypeService {

    private final TypeRepository typeRepository;
    private final PokemonRepository pokemonRepository;

    /** All 18 types. */
    public List<TypeDto> findAll() {
        return typeRepository.findAllByOrderByIdAsc().stream().map(TypeDto::from).toList();
    }

    public TypeDto findById(Integer id) {
        return typeRepository.findById(id).map(TypeDto::from).orElseThrow(() -> new NotFoundException("Type", id));
    }

    /** Pokémon that have the type as primary or secondary type. */
    public List<PokemonSummaryDto> findPokemon(Integer id) {
        if (!typeRepository.existsById(id)) {
            throw new NotFoundException("Type", id);
        }
        return pokemonRepository.findByTypes_Type_IdOrderByIdAsc(id).stream().map(PokemonSummaryDto::from).toList();
    }
}
