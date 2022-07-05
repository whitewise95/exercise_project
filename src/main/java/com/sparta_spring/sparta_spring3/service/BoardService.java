package com.sparta_spring.sparta_spring3.service;

import com.sparta_spring.sparta_spring3.domain.Board;
import com.sparta_spring.sparta_spring3.dto.RequestBlogDto;
import com.sparta_spring.sparta_spring3.repository.BoardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Transactional
    public void blogUpdate(Long id, RequestBlogDto requestBlogDto) {
        Board board = findOneBolg(id);
        if (!isPasswordCheck(board.getPassword(), requestBlogDto.getPassword())) {
            throw new RuntimeException(("비밀번호가 일치하지 않습니다."));
        }
        if (Optional.ofNullable(requestBlogDto.getImgUrl()).isPresent()) {
            if (!board.getImgUrl().equals(requestBlogDto.getImgUrl())) {
                board.updateWiteImage(requestBlogDto);
                return;
            }
        }
        board.update(requestBlogDto);
    }

    @Transactional
    public void blogDelete(Long id, RequestBlogDto requestBlogDto) {
        if (isPasswordCheck(findOneBolg(id).getPassword(), requestBlogDto.getPassword())) {
            boardRepository.deleteById(id);
        } else {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }
    }

    @Transactional
    public Board findOneBolg(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디가 존재하지 않습니다."));
    }

    public boolean isPasswordCheck(String dbPassword, String requestPassword) {
        return dbPassword.equals(requestPassword);
    }
}
