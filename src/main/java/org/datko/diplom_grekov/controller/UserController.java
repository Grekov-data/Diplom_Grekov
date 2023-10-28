package org.datko.diplom_grekov.controller;

import lombok.RequiredArgsConstructor;
import org.datko.diplom_grekov.entity.User;
import org.datko.diplom_grekov.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;


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

    @GetMapping("/user-details")                                          //лист с детальной информацией о пользователе
    public String details(Model model) {
        Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = ((UserDetails)object).getUsername();
        Optional<User> user = userService.findByLogin(login);
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
            model.addAttribute("completedSurv", user.get().getClient().getCompletedSurveys());
        } else {
            model.addAttribute("user", null);
        }
        return "user/user-details";
    }

}
