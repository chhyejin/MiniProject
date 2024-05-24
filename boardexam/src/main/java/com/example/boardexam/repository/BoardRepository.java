package com.example.boardexam.repository;

import com.example.boardexam.domain.Board;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface BoardRepository extends CrudRepository<Board, Long>, PagingAndSortingRepository<Board,Long> {
}
