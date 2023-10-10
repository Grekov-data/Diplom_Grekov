package org.datko.diplom_grekov.controller;

import lombok.RequiredArgsConstructor;
import org.datko.diplom_grekov.entity.Company;
import org.datko.diplom_grekov.service.CompanyService;
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
@RequestMapping("company")
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping("")                                                 //вывод листа со списком компаний
    public String findAll(Model model){
        Iterable<Company> companies = companyService.findAll();
        if (companies.iterator().hasNext()) {
            model.addAttribute("companies", companies);
        } else {
            model.addAttribute("companies", null);
        }
        return "company/company-list";
    }

    @GetMapping("new")                                              //переход на лист для создания новой компании
    public String addNew(Model model) {
        Company company = new Company();
        model.addAttribute("company", company);
        return "company/company-form";
    }

    @PostMapping("new")                                             //создание новой компании
    public String addNew(Company company, RedirectAttributes ra) {
        Optional<Company> newCompany = companyService.add(company);
        if(newCompany.isPresent()) {
            ra.addFlashAttribute("successMessage",
                    "Компания " + company.getName() + " успешно добавлена!");
        } else {
            ra.addFlashAttribute("errorMessage",
                    "Компания " + company.getName() + " уже зарегистрирована!");
        }
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")                                      //удаление компании
    public String delete(@PathVariable Integer id, RedirectAttributes ra) {
        Optional<Company> removed = companyService.deleteById(id);
        if(removed.isPresent()) {
            ra.addFlashAttribute("successMessage",
                    "Компания " + removed.get().getName() + " успешно удалена!");
        } else {
            ra.addFlashAttribute("errorMessage",
                    "Некорректный id для удаления!");
        }
        return "redirect:/company/";
    }

    @GetMapping("{id}")                                             //лист с детальной информацией о компании
    public String details(@PathVariable Integer id, Model model) {
        Optional<Company> company = companyService.findById(id);
        if (company.isPresent()) {
            model.addAttribute("company", company.get());
        } else {
            model.addAttribute("company", null);
        }
        return "company/company-details";
    }

    @GetMapping("/update/{id}")                         //переход на лист для обновления данных существующей компании
    public String updateExisting(@PathVariable Integer id, Model model) {
        Optional<Company> company = companyService.findById(id);
        if (company.isPresent()) {
            model.addAttribute("company", company.get());
        } else {
            model.addAttribute("company", null);
        }
        return "company/company-form-update";
    }

    @PostMapping("/update/{id}")                        //обновление данных уже существующей компании
    public String updateExisting(@PathVariable Integer id, Company company, RedirectAttributes ra) {
        Optional<Company> updated = companyService.updateById(id, company);
        if (updated.isPresent()) {
            ra.addFlashAttribute("successMessage",
                    "Компания успешно обновлена");
        } else {
            ra.addFlashAttribute("errorMessage",
                    "Компания не была обновлена");
        }
        return "redirect:/company/{id}";
    }
}
