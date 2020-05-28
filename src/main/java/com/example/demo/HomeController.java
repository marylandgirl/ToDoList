package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class HomeController {

    @Autowired
    ToDoRepository toDoRepository;

    @RequestMapping("/")
    public String listToDos(Model model) {
        model.addAttribute("toDos",toDoRepository.findAll());
        return "list";
    }

    @GetMapping("/add")
    public String toDoForm(Model model) {
        model.addAttribute("toDo", new ToDo());
        return "todoform";
    }

    @PostMapping("/process")
    public String processForm(@Valid @ModelAttribute ToDo toDo, BindingResult result) {
        if (result.hasErrors()) {
            return "todoform";
        }
        toDoRepository.save(toDo);
        return "redirect:/";
    }

    @RequestMapping("/update/{id}")
    public String updateToDo(@PathVariable("id") long id, Model model) {
        model.addAttribute(toDoRepository.findById(id).get());
        return "todoform";
    }

    @RequestMapping("/detail/{id}")
    public String showToDo(@PathVariable("id") long id, Model model) {
        model.addAttribute("toDo",toDoRepository.findById(id).get());
        return "show";
    }

    @RequestMapping("/delete/{id}")
    public String deleteToDo(@PathVariable ("id") long id, Model model) {
        toDoRepository.deleteById(id);
        return "redirect:/";
    }
}
