package com.pokemonlb.repository;

import com.pokemonlb.model.entity.Type;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeRepository extends JpaRepository<Type, Integer> {

    List<Type> findAllByOrderByIdAsc();

    Optional<Type> findByNameIgnoreCase(String name);
}
