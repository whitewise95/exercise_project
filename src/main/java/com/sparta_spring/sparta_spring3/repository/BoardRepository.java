package com.sparta_spring.sparta_spring3.repository;

import com.sparta_spring.sparta_spring3.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findAllByOrderByCreateDateDesc();
}
