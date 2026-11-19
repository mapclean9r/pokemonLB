package com.pokemonlb.service;

import com.pokemonlb.exception.NotFoundException;
import com.pokemonlb.model.dto.GameDto;
import com.pokemonlb.model.dto.MoveDto;
import com.pokemonlb.model.entity.Move;
import com.pokemonlb.repository.MoveGameAvailabilityRepository;
import com.pokemonlb.repository.MoveRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MoveService {

    private final MoveRepository moveRepository;
    private final MoveGameAvailabilityRepository moveAvailabilityRepository;

    /** All moves by name, optionally only one type (e.g. "Electric"). */
    public List<MoveDto> findAll(String type) {
        List<Move> moves = type == null
                ? moveRepository.findAllByOrderByNameAscIdAsc()
                : moveRepository.findByTypeIgnoreCaseOrderByNameAscIdAsc(type);
        return moves.stream().map(MoveDto::from).toList();
    }

    public MoveDto findById(Integer id) {
        return moveRepository.findById(id).map(MoveDto::from).orElseThrow(() -> new NotFoundException("Move", id));
    }

    /** Games the move exists in. */
    public List<GameDto> findGames(Integer id) {
        if (!moveRepository.existsById(id)) {
            throw new NotFoundException("Move", id);
        }
        return moveAvailabilityRepository.findByMoveIdOrderByGenerationAscGameIdAsc(id).stream()
                .map(GameDto::from)
                .toList();
    }
}
