package org.datko.diplom_grekov.controller;

import lombok.RequiredArgsConstructor;
import org.datko.diplom_grekov.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/registration")
    public String registration(
            @RequestParam String login,
            @RequestParam String password,
            @RequestParam String passwordConfirmation,
            RedirectAttributes ra
    ) {
        if (userService.register(login, password, passwordConfirmation)) {
            ra.addFlashAttribute("successMessage", "Вы успешно зарегистрированы!");
        } else {
            ra.addFlashAttribute("errorMessage", "Ошибка регистрации");
        }
        return "redirect:/login";
    }
}
