package com.sparta_spring.sparta_spring3.controller;

import com.sparta_spring.sparta_spring3.domain.Board;
import com.sparta_spring.sparta_spring3.dto.RequestBlogDto;
import com.sparta_spring.sparta_spring3.repository.BoardRepository;
import com.sparta_spring.sparta_spring3.security.UserDetailsImpl;
import com.sparta_spring.sparta_spring3.service.BoardService;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BoardController {

    private final BoardService boardService;
    private final BoardRepository boardRepository;

    public BoardController(BoardService boardService, BoardRepository boardRepository) {
        this.boardService = boardService;
        this.boardRepository = boardRepository;
    }

    @Controller
    public class BlogViewController {

        @GetMapping("/")
        public String blogView(Model model,
                               @PageableDefault(size = 6, sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable,
                               @RequestParam(value = "currentPage", defaultValue = "1") int page) {

            model.addAttribute("pageable", boardRepository.findAll(pageable));
            model.addAttribute("page", page);
            return "index";
        }
    }

    @GetMapping("/board")
    public List<Board> blogFindAll() {
        return boardRepository.findAllByOrderByCreateDateDesc();
    }

    @PostMapping("/board")
    public Long blogWrite(@RequestBody Board board) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl user = (UserDetailsImpl)authentication.getPrincipal();
        return boardRepository.save(board).getId();
    }

    @PutMapping("/board/{id}")
    public Long blogUpdate(@PathVariable Long id,
                           @RequestBody RequestBlogDto requestBlogDto) {
        boardService.blogUpdate(id, requestBlogDto);
        return id;
    }

    @DeleteMapping("/board/{id}")
    public Long blogDelete(@PathVariable Long id,
                           @RequestBody RequestBlogDto requestBlogDto) {
        boardService.blogDelete(id, requestBlogDto);
        return id;
    }

}
