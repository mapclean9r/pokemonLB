package com.pokemonlb.repository;

import com.pokemonlb.model.entity.Move;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoveRepository extends JpaRepository<Move, Integer> {

    List<Move> findAllByOrderByNameAscIdAsc();

    List<Move> findByTypeIgnoreCaseOrderByNameAscIdAsc(String type);
}
