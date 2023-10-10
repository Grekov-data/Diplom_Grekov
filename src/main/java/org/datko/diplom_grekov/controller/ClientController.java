package org.datko.diplom_grekov.controller;

import lombok.RequiredArgsConstructor;
import org.datko.diplom_grekov.entity.Client;
import org.datko.diplom_grekov.service.ClientService;
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
@RequestMapping("client")
public class ClientController {

    private final ClientService clientService;

    @GetMapping("")                                              //вывод листа со списком пользователей
    public String findAll(Model model){
        Iterable<Client> clients = clientService.findAll();
        if (clients.iterator().hasNext()) {
            model.addAttribute("clients", clients);
        } else {
            model.addAttribute("clients", null);
        }
        return "client/client-list";
    }

    @GetMapping("new")                                           //переход на лист для создания нового пользователя
    public String addNew(Model model) {
        Client client = new Client();
        model.addAttribute("client", client);
        return "client/client-form";
    }

    @PostMapping("new")                                          //создание учётной записи пользователя
    public String addNew(Client client, RedirectAttributes ra) {
        Optional<Client> newClient = clientService.add(client);
        if(newClient.isPresent()) {
            ra.addFlashAttribute("successMessage",
                    "Пользователь " + client.getName() + " успешно зарегистрирован!");
        } else {
            ra.addFlashAttribute("errorMessage",
                    "Пользователь с эл.почтой " + client.getEmail() + " уже зарегистрирован!");
        }
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")                                  //удаление пользователя
    public String delete(@PathVariable Integer id, RedirectAttributes ra) {
        Optional<Client> removed = clientService.deleteById(id);
        if(removed.isPresent()) {
            ra.addFlashAttribute("successMessage",
                    "Пользователь " + removed.get().getName() + " успешно удалён!");
        } else {
            ra.addFlashAttribute("errorMessage",
                    "Некорректный id для удаления!");
        }
        return "redirect:/client";
    }

    @GetMapping("{id}")                                          //лист с детальной информацией о пользователе
    public String details(@PathVariable Integer id, Model model) {
        Optional<Client> client = clientService.findById(id);
        if (client.isPresent()) {
            model.addAttribute("client", client.get());
        } else {
            model.addAttribute("client", null);
        }
        return "client/client-details";
    }

    @GetMapping("/update/{id}")                    //переход на лист для обновления данных существующего пользователя
    public String updateExisting(@PathVariable Integer id, Model model) {
        Optional<Client> client = clientService.findById(id);
        if (client.isPresent()) {
            model.addAttribute("client", client.get());
        } else {
            model.addAttribute("client", null);
        }
        return "client/client-form-update";
    }

    @PostMapping("/update/{id}")                        //обновление данных уже существующей компании
    public String updateExisting(@PathVariable Integer id, Client client, RedirectAttributes ra) {
        Optional<Client> updated = clientService.updateById(id, client);
        if (updated.isPresent()) {
            ra.addFlashAttribute("successMessage",
                    "Данные пользователя успешно обновлены");
        } else {
            ra.addFlashAttribute("errorMessage",
                    "Данные пользователя не были обновлены");
        }
        return "redirect:/client/{id}";
    }
}
