package com.rost.srg.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/calculator")
public class Calculator {
    @GetMapping
    public String calculate(@RequestParam("a") int a,
                            @RequestParam("b") int b,
                            @RequestParam("action") Action action,
                            Model model)
    {
        String message;
        switch (action) {
            case MULTIPLICATION:
                message = String.format("a * b = %d", a * b);
                break;
            case ADDITION:
                message = String.format("a + b = %d", a + b);
                break;
            case SUBTRACTION:
                message = String.format("a - b = %d", a - b);
                break;
            case DIVISION:
                message = String.format("a / b = %d", a / b);
                break;
            default:
                message = "Incorrect params!";
        }
        model.addAttribute("message", message);
        return "calculator/calculate";
    }

    private enum Action {
        MULTIPLICATION,
        ADDITION,
        SUBTRACTION,
        DIVISION
    }
}
