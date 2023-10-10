package org.datko.diplom_grekov.controller;

import lombok.RequiredArgsConstructor;
import org.datko.diplom_grekov.entity.Company;
import org.datko.diplom_grekov.entity.ObjectSurv;
import org.datko.diplom_grekov.entity.Survey;
import org.datko.diplom_grekov.service.CompanyService;
import org.datko.diplom_grekov.service.ObjectSurvService;
import org.datko.diplom_grekov.service.SurveyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
@RequestMapping("survey")
public class SurveyController {

    private final SurveyService surveyService;
    private final CompanyService companyService;
    private final ObjectSurvService objectSurvService;

    @GetMapping("")                                                 //вывод листа со списком опросов
    public String findAll(Model model){
        Iterable<Survey> surveys = surveyService.findAll();
        if (surveys.iterator().hasNext()) {
            model.addAttribute("surveys", surveys);
        } else {
            model.addAttribute("surveys", null);
        }
        return "survey/survey-list";
    }

    @GetMapping("new")                                              //переход на лист для создания нового опроса
    public String addNew(Model model) {
        Survey survey = new Survey();
        model.addAttribute("survey", survey);
        Iterable<Company> companies = companyService.findAll();
        model.addAttribute("companies", companies);
        return "survey/survey-form";
    }

    @GetMapping("new/{companyId}")               //переход на лист для создания нового опроса для конкретной компании
    public String addNew(@PathVariable Integer companyId, Model model, RedirectAttributes ra) {
        Optional<Company> company = companyService.findById(companyId);
        model.addAttribute("company", company);
        if (company.isEmpty()) {
            ra.addFlashAttribute("errorMessage", "Компания не найдена");
            return "redirect:/";
        }
        Survey survey = new Survey();
        model.addAttribute("survey", survey);
        Iterable<Company> companies = List.of(company.get());
        model.addAttribute("companies", companies);
        return "survey/survey-form";
    }

    @PostMapping("new")                                             //создание нового опроса
    public String addNew(Survey survey, RedirectAttributes ra) {
        Optional<Survey> newSurvey = surveyService.add(survey.getCompany().getId(), survey);
        if(newSurvey.isPresent()) {
            ra.addFlashAttribute("successMessage",
                    "Опрос \"" + survey.getName() + "\" успешно добавлен!");
        } else {
            ra.addFlashAttribute("errorMessage",
                    "Опрос \"" + survey.getName() + "\" уже зарегистрирован!");
        }
        return "redirect:/company/" + survey.getCompany().getId();
    }

    @GetMapping("/delete/{id}")                                      //удаление опроса
    public String delete(@PathVariable Integer id, Survey survey, RedirectAttributes ra) {
        Optional<Survey> removed = surveyService.deleteById(id);
        if(removed.isPresent()) {
            ra.addFlashAttribute("successMessage",
                    "Опрос " + removed.get().getName() + " успешно удален!");
        } else {
            ra.addFlashAttribute("errorMessage",
                    "Некорректный id для удаления!");
        }
        return "redirect:/company/" + removed.get().getCompany().getId();
    }

    @GetMapping("{id}")                                             //лист с детальной информацией об опросе
    public String details(@PathVariable Integer id, Model model) {
        Optional<Survey> survey = surveyService.findById(id);
        ObjectSurv objectSurv = new ObjectSurv();
        model.addAttribute("objectSurv", objectSurv);
        Iterable<Survey> surveys = List.of(survey.get());
        model.addAttribute("surveys", surveys);
        if (survey.isPresent()) {
            model.addAttribute("survey", survey.get());
        } else {
            model.addAttribute("survey", null);
        }
        return "survey/survey-details";
    }

    @GetMapping("/update/{id}")                         //переход на лист для обновления данных существующего опроса
    public String updateExisting(@PathVariable Integer id, Model model) {
        Optional<Survey> survey = surveyService.findById(id);
        if (survey.isPresent()) {
            model.addAttribute("survey", survey.get());
        } else {
            model.addAttribute("survey", null);
        }
        return "survey/survey-form-update";
    }

    @PostMapping("/update/{id}")                        //обновление данных уже существующего опроса
    public String updateExisting(@PathVariable Integer id, Survey survey, RedirectAttributes ra) {
        Optional<Survey> updated = surveyService.updateById(id, survey);
        if (updated.isPresent()) {
            ra.addFlashAttribute("successMessage",
                    "Опрос успешно обновлен");
        } else {
            ra.addFlashAttribute("errorMessage",
                    "Опрос не был обновлен");
        }
        return "redirect:/survey/{id}";
    }

    @PostMapping("/changeIsActive/{id}")                                  //увеличение рейтинга для объекта
    public String changeIsActive(@PathVariable Integer id, Survey survey, RedirectAttributes ra) {
        Optional<Survey> updated = surveyService.changeIsActive(id, survey);
        if (updated.isPresent()) {
            ra.addFlashAttribute("successMessage",
                    "Режим отображения опроса изменён!");
        } else {
            ra.addFlashAttribute("errorMessage",
                    "Режим отображения опроса не был изменён");
        }
        return "redirect:/survey/{id}";
    }

    @GetMapping("/take-survey/{id}")                                        //лист с детальной информацией об опросе
    public String takeSurvey(@PathVariable Integer id, Model model) {
        Optional<Survey> survey = surveyService.findById(id);
        if (survey.isPresent()) {
            model.addAttribute("survey", survey.get());
        } else {
            model.addAttribute("survey", null);
        }
        return "survey/take-survey";
    }
}
