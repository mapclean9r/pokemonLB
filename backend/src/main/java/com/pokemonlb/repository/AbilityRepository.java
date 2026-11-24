package com.pokemonlb.repository;

import com.pokemonlb.model.entity.Ability;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AbilityRepository extends JpaRepository<Ability, Integer> {

    List<Ability> findAllByOrderByNameAscIdAsc();

    List<Ability> findByGenerationOrderByNameAscIdAsc(Short generation);
}
