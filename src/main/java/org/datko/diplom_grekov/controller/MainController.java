package org.datko.diplom_grekov.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("")                                         //запуск главной страницы
    public String index(){
        return "index";
    }

    @GetMapping("/log-in")
    public String logIn(){
        return "authorization/log-in";
    }

    @GetMapping("/registration")
    public String registration(){
        return "authorization/registration";
    }
}
