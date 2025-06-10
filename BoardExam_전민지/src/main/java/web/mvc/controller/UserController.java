package web.mvc.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import web.mvc.domain.User;
import web.mvc.service.UserService;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/user/{url}")
    public String loginForm(@PathVariable String url){
        return "/user/"+url;
    }

    @PostMapping("/user/loginCheck")
    public String loginCheck(User user, HttpSession session){
        if(userService.loginCheck(user)!=null){
                 session.setAttribute("loginUser", user);}
        return "redirect:/";
    }

    @GetMapping("/user/logout")
    public String logout(HttpSession session){
        if(session.getAttribute("loginUser")!=null){session.invalidate();}

      return "redirect:/";
}
}
