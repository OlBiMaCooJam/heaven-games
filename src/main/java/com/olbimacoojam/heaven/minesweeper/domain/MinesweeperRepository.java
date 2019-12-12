package com.olbimacoojam.heaven.minesweeper.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MinesweeperRepository extends JpaRepository<Board, Long> {
}
