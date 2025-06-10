package web.mvc.controller;


import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.mvc.domain.FreeBoard;
import web.mvc.exception.BasicException;
import web.mvc.exception.ErrorCode;
import web.mvc.service.FreeBoardService;

import java.util.List;

@Controller
@RequestMapping("/board")
public class FreeBoardController {
    private static final int PAGE_SIZE=7;
    private static final int BLOCK_CNT=3;
    @Autowired
    private FreeBoardService freeBoardService;
    @GetMapping("/{url}")
    public String board(@PathVariable String url,Model model) {
        return "board/" + url;
    }
   /* @GetMapping("/list")
    public String selectAll(Model model) {
        List<FreeBoard> list=freeBoardService.selectAll();
        model.addAttribute("freeList",list);
        return "/board/list";
    }*/

    @GetMapping("/list")
    public void selectAllPage(Model model, @RequestParam(defaultValue = "1") Integer nowPage, HttpSession session) {
        if(session.getAttribute("loginUser") == null) throw new BasicException(ErrorCode.ACCESS_DENIED);
        Page<FreeBoard> page=freeBoardService.selectAll(PageRequest.of(nowPage-1,PAGE_SIZE));
        model.addAttribute("pageList",page);
        model.addAttribute("nowPage",nowPage);
        model.addAttribute("blockCount",BLOCK_CNT);
        model.addAttribute("startPage",nowPage-((nowPage-1)%BLOCK_CNT));

    }
    @GetMapping("/read/{bno}")
    public String read(@PathVariable Long bno, Model model,@RequestParam(defaultValue = "true") boolean state) {
       FreeBoard board= freeBoardService.selectBy(bno,state);
       model.addAttribute("board",board);
        return "/board/read";
    }

    @PostMapping("/insert")
    public String insert(FreeBoard freeBoard) {
        freeBoardService.insert(freeBoard);
        return "redirect:/board/list";
    }
    @PostMapping("/updateForm")
    public String updateForm(FreeBoard freeBoard,Model model) {
        FreeBoard board =freeBoardService.selectBy(freeBoard.getBno(),false);
        model.addAttribute("board",board);
        return "/board/update";
    }

    @PostMapping("/update")
    public String update(FreeBoard freeBoard) {
        freeBoardService.update(freeBoard);
        return "redirect:/board/read/"+freeBoard.getBno()+"?state=false";
    }

    @PostMapping("/delete")
    public String delete(FreeBoard freeBoard) {
        freeBoardService.delete(freeBoard.getBno(),freeBoard.getPassword());
        return  "redirect:/board/list";
    }


}
