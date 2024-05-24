package com.example.boardexam.controller;

import com.example.boardexam.domain.Board;
import com.example.boardexam.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequiredArgsConstructor
public class BoardController {
    //게시글 목록 보기 (/list or /list?page=2)
    private final BoardService boardService;
    @GetMapping("/list")
    public String board(Model model, @RequestParam(defaultValue = "1")int page,
                          @RequestParam(defaultValue = "5") int size){
        Pageable pageable = PageRequest.of(page -1 , size);
        Page<Board> board =  boardService.findAllBoard(pageable);
        model.addAttribute("board", board);
        model.addAttribute("currentPage",page);
        return "list";
    }
    //게시글 상세 조회 (/view?id=아이디)
    @GetMapping("/view")
    public String detailShow(@RequestParam(name="id", required = false) Long id, Model model){
        Board board=boardService.findBoard(id);
        //날짜 정보
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        model.addAttribute("board",board);
        return "detail";
    }
    //게시글 등록 폼(/writeform)
    @GetMapping("/writeform")
    public String writeForm(Model model){
        model.addAttribute("board",new Board());
        return "write";
    }
    @PostMapping("/write")
    public String add(@ModelAttribute Board board){
        board.setCreatedAt(LocalDateTime.now());
        board.setUpdatedAt(LocalDateTime.now());
        boardService.saveBoard(board);
        return "redirect:/list";
    }
    //게시글 삭제 폼(/deleteform?id=아이디)
    @GetMapping("/deleteform")
    public String deleteForm(@RequestParam(name="id", required = false) Long id, Model model){
        model.addAttribute("board", boardService.findBoard(id));
        return "delete";
    }
    @PostMapping("/delete")
    public String delete(@RequestParam("id") Long id, @RequestParam("password") String password, Model model){
        Board board = boardService.findBoard(id);
        if (board != null && board.getPassword().equals(password)) {
            boardService.deleteBoard(id);
            return "redirect:/list";
        } else {
            model.addAttribute("error", "Invalid password.");
            model.addAttribute("board", board);
            return "delete";
        }
    }
    //게시글 수정 폼(/updateform?id=아이디)
    @GetMapping("/updateform")
    public String updateForm(@RequestParam(name="id", required = false) Long id, Model model){
        Board board=boardService.findBoard(id);
//        board.setCreatedAt(LocalDateTime.now());
        board.setUpdatedAt(LocalDateTime.now());
        model.addAttribute("id",boardService.findBoard(id));
        model.addAttribute("board", board);
        return "update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Board board){
        boardService.saveBoard(board);
        return "redirect:/view?id="+board.getId();
    }
}
