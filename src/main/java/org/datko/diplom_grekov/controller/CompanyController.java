package org.datko.diplom_grekov.controller;

import lombok.RequiredArgsConstructor;
import org.datko.diplom_grekov.entity.Company;
import org.datko.diplom_grekov.service.CompanyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String addNew() {
        return "";
    }

}
