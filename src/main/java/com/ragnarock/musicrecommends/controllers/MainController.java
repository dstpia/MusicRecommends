package com.ragnarock.musicrecommends.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Главная страница");
        return "home";
    }

    @GetMapping("/About")
    public String about(Model model) {
        model.addAttribute("title", "О нас");
        return "about";
    }

    @GetMapping("/Genres")
    public String genres(Model model) {
        model.addAttribute("title", "Жанры");
        return "genres";
    }

    @GetMapping("/Authors")
    public String authors(Model model) {
        model.addAttribute("title", "Авторы");
        return "authors";
    }

    @GetMapping("/Years")
    public String years(Model model) {
        model.addAttribute("title", "Года");
        return "years";
    }
}