package org.datko.diplom_grekov.controller;

import lombok.RequiredArgsConstructor;
import org.datko.diplom_grekov.entity.Client;
import org.datko.diplom_grekov.entity.ObjectSurv;
import org.datko.diplom_grekov.entity.User;
import org.datko.diplom_grekov.service.ClientService;
import org.datko.diplom_grekov.service.ObjectSurvService;
import org.datko.diplom_grekov.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
@RequestMapping("objectSurv")
public class ObjectSurvController {

    private final ObjectSurvService objectSurvService;
    private final UserService userService;

    @GetMapping("")                                                 //вывод листа со списком объектов
    public String findAll(Model model){
        Iterable<ObjectSurv> objectSurvs = objectSurvService.findAll();
        if (objectSurvs.iterator().hasNext()) {
            model.addAttribute("objectSurvs", objectSurvs);
        } else {
            model.addAttribute("objectSurvs", null);
        }
        return "object/object-list";
    }

    @PostMapping("new")                                             //создание нового опроса
    public String addNew(ObjectSurv objectSurv, RedirectAttributes ra) {
        Optional<ObjectSurv> newObjectSurv = objectSurvService.add(objectSurv.getSurvey().getId(), objectSurv);
        if(newObjectSurv.isPresent()) {
            ra.addFlashAttribute("successMessage",
                    "Объект \"" + objectSurv.getName() + "\" успешно добавлен!");
        } else {
            ra.addFlashAttribute("errorMessage",
                    "Объект \"" + objectSurv.getName() + "\" уже зарегистрирован!");
        }
        return "redirect:/survey/" + objectSurv.getSurvey().getId();
    }

    @GetMapping("/delete/{id}")                                      //удаление опроса
    public String delete(@PathVariable Integer id, ObjectSurv objectSurv, RedirectAttributes ra) {
        Optional<ObjectSurv> removed = objectSurvService.deleteById(id);
        if(removed.isPresent()) {
            ra.addFlashAttribute("successMessage",
                    "Объект опроса " + removed.get().getName() + " успешно удален!");
        } else {
            ra.addFlashAttribute("errorMessage",
                    "Некорректный id для удаления!");
        }
        return "redirect:/survey/" + removed.get().getSurvey().getId();
    }

    @PostMapping("/upRating/{id}")                                  //увеличение рейтинга для объекта
    public String updateExisting(@PathVariable Integer id, ObjectSurv objectSurv, Model model/*, RedirectAttributes ra*/) {

        UserDetails userDetails = (UserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        String login = userDetails.getUsername();
        Optional<User> user = userService.findByLogin(login);
        Client client = user.get().getClient();

        Optional<ObjectSurv> updated = objectSurvService.upRating(id, objectSurv, client);

        return "redirect:/survey/" + objectSurv.getSurvey().getId();
    }
}
