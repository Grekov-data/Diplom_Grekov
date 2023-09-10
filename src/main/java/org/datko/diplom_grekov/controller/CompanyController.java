package org.datko.diplom_grekov.controller;

import lombok.RequiredArgsConstructor;
import org.datko.diplom_grekov.entity.Company;
import org.datko.diplom_grekov.service.CompanyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@RequiredArgsConstructor
@Controller
@RequestMapping("company")
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping("")
    public String findAll(Model model){
        Iterable<Company> companies = companyService.findAll();
        if (companies.iterator().hasNext()) {
            model.addAttribute("companies", companies);
        } else {
            model.addAttribute("companies", null);
        }
        return "company/company-list";
    }

    @GetMapping("new")
    public String addNew(Model model) {
        Company company = new Company();
        model.addAttribute("company", company);
        return "/company/company-form";
    }

    @PostMapping("new")
    public String addNew(Company company, RedirectAttributes ra) {
        Optional<Company> newCompany = companyService.add(company);
        if(newCompany.isPresent()) {
            ra.addFlashAttribute("successMessage",
                    "Компания " + company.getName() + " успешно добавлена!");
        } else {
            ra.addFlashAttribute("errorMessage",
                    "Компания " + company.getName() + " уже зарегистрирована!");
        }
        return "redirect:/company";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes ra) {
        Optional<Company> removed = companyService.deleteById(id);
        if(removed.isPresent()) {
            ra.addFlashAttribute("successMessage",
                    "Компания " + removed.get().getName() + " успешно удалена!");
        } else {
            ra.addFlashAttribute("errorMessage",
                    "Некорректный id для удаления!");
        }
        return "redirect:/company";
    }
}
