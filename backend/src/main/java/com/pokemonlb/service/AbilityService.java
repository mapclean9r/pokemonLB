package com.pokemonlb.service;

import com.pokemonlb.exception.NotFoundException;
import com.pokemonlb.model.dto.AbilityDto;
import com.pokemonlb.model.dto.AbilityPokemonDto;
import com.pokemonlb.model.entity.Ability;
import com.pokemonlb.repository.AbilityRepository;
import com.pokemonlb.repository.PokemonAbilityRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AbilityService {

    private final AbilityRepository abilityRepository;
    private final PokemonAbilityRepository pokemonAbilityRepository;

    /** All abilities by name, optionally only those introduced in one generation. */
    public List<AbilityDto> findAll(Short generation) {
        List<Ability> abilities = generation == null
                ? abilityRepository.findAllByOrderByNameAscIdAsc()
                : abilityRepository.findByGenerationOrderByNameAscIdAsc(generation);
        return abilities.stream().map(AbilityDto::from).toList();
    }

    public AbilityDto findById(Integer id) {
        return abilityRepository.findById(id).map(AbilityDto::from).orElseThrow(() -> new NotFoundException("Ability", id));
    }

    /** Pokémon that can have the ability, by Pokédex number, each marked hidden or not. */
    public List<AbilityPokemonDto> findPokemon(Integer id) {
        if (!abilityRepository.existsById(id)) {
            throw new NotFoundException("Ability", id);
        }
        return pokemonAbilityRepository.findByAbilityId(id).stream().map(AbilityPokemonDto::from).toList();
    }
}
