package com.sparta_spring.sparta_spring3.repository;

import com.sparta_spring.sparta_spring3.domain.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findAllByOrderByCreateDateDesc();

    Page<Board> findByUserId(Long userId, Pageable pageable);
}
