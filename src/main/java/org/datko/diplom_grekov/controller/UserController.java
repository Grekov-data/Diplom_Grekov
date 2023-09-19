package org.datko.diplom_grekov.controller;

import lombok.RequiredArgsConstructor;
import org.datko.diplom_grekov.entity.User;
import org.datko.diplom_grekov.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@RequiredArgsConstructor
@Controller
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    @GetMapping("")                                              //вывод листа со списком пользователей
    public String findAll(Model model){
        Iterable<User> users = userService.findAll();
        if (users.iterator().hasNext()) {
            model.addAttribute("users", users);
        } else {
            model.addAttribute("users", null);
        }
        return "user/user-list";
    }

    @GetMapping("new")                                           //переход на лист для создания нового пользователя
    public String addNew(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "user/user-form";
    }

    @PostMapping("new")                                          //создание учётной записи пользователя
    public String addNew(User user, RedirectAttributes ra) {
        Optional<User> newUser = userService.add(user);
        if(newUser.isPresent()) {
            ra.addFlashAttribute("successMessage",
                    "Пользователь " + user.getName() + " успешно зарегистрирован!");
        } else {
            ra.addFlashAttribute("errorMessage",
                    "Пользователь с эл.почтой " + user.getEmail() + " уже зарегистрирован!");
        }
        return "redirect:/user";
    }

    @GetMapping("/delete/{id}")                                  //удаление пользователя
    public String delete(@PathVariable Integer id, RedirectAttributes ra) {
        Optional<User> removed = userService.deleteById(id);
        if(removed.isPresent()) {
            ra.addFlashAttribute("successMessage",
                    "Пользователь " + removed.get().getName() + " успешно удалён!");
        } else {
            ra.addFlashAttribute("errorMessage",
                    "Некорректный id для удаления!");
        }
        return "redirect:/user";
    }

    @GetMapping("{id}")                                          //лист с детальной информацией о пользователе
    public String details(@PathVariable Integer id, Model model) {
        Optional<User> user = userService.findById(id);
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
        } else {
            model.addAttribute("user", null);
        }
        return "user/user-details";
    }

    @GetMapping("/update/{id}")                    //переход на лист для обновления данных существующего пользователя
    public String updateExisting(@PathVariable Integer id, Model model) {
        Optional<User> user = userService.findById(id);
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
        } else {
            model.addAttribute("user", null);
        }
        return "user/user-form-update";
    }

    @PostMapping("/update/{id}")                        //обновление данных уже существующей компании
    public String updateExisting(@PathVariable Integer id, User user, RedirectAttributes ra) {
        Optional<User> updated = userService.updateById(id, user);
        if (updated.isPresent()) {
            ra.addFlashAttribute("successMessage",
                    "Данные пользователя успешно обновлены");
        } else {
            ra.addFlashAttribute("errorMessage",
                    "Данные пользователя не были обновлены");
        }
        return "redirect:/user/{id}";
    }
}
