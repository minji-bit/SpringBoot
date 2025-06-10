package web.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import web.mvc.domain.FreeBoard;
import web.mvc.domain.Reply;
import web.mvc.service.ReplyService;

@Controller
@RequestMapping("/reply")
public class ReplyController {
    @Autowired
    private ReplyService replyService;

    @PostMapping("/writeForm")
    public String writeForm(Long bno, Model model) {
        model.addAttribute("bno", bno);
        return "reply/write";
    }
    @PostMapping("/insert")
    public String insert(Long bno, String content, Model model) {
        replyService.insert(Reply.builder().content(content).freeBoard(FreeBoard.builder().bno(bno).build()).build());
        return  "redirect:/board/read/"+bno+"?state=false";
    }

    @GetMapping("/delete/{rno}/{bno}")
    public String delete(@PathVariable("rno") Long rno, @PathVariable("bno") Long bno) {
        replyService.delete(rno);
        return "redirect:/board/read/"+bno+"?state=false";
    }


}
