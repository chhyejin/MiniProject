package com.example.boardexam.service;

import com.example.boardexam.domain.Board;
import com.example.boardexam.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    @Transactional(readOnly = true)
    public Iterable<Board> findAllBoard(){
        return boardRepository.findAll();
    }
    public Page<Board> findAllBoard(Pageable pageable){
        Pageable sortedByDescId =  PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC,"id"));

        return boardRepository.findAll(sortedByDescId);
    }
    @Transactional(readOnly = true)
    public Board findBoard(Long id){
        return boardRepository.findById(id).orElse(null);
    }
    @Transactional
    public Board saveBoard(Board board){
        return boardRepository.save(board);
    }
    @Transactional
    public void deleteBoard(Long id){
        boardRepository.deleteById(id);
    }
}
