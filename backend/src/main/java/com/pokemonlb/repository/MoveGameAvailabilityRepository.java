package com.pokemonlb.repository;

import com.pokemonlb.model.entity.MoveGameAvailability;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoveGameAvailabilityRepository
        extends JpaRepository<MoveGameAvailability, MoveGameAvailability.Key> {

    /** Games a move exists in, oldest generation first. */
    List<MoveGameAvailability> findByMoveIdOrderByGenerationAscGameIdAsc(Integer moveId);

    /** All moves that exist in a game. */
    List<MoveGameAvailability> findByGameIdOrderByMoveName(Integer gameId);
}
