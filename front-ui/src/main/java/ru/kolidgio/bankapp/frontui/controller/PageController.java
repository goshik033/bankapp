package ru.kolidgio.bankapp.frontui.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import ru.kolidgio.bankapp.frontui.client.AccountClient;
import ru.kolidgio.bankapp.frontui.client.ExchangeClient;
import ru.kolidgio.bankapp.frontui.dto.CreateUserDto;

@Controller
@RequiredArgsConstructor
public class PageController {
    private final AccountClient accountClient;
    private final ExchangeClient exchangeClient;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new CreateUserDto("",
                "",
                "",
                "",
                null,
                ""));
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") CreateUserDto createUserDto,
                           BindingResult bindingResult,
                           Model model) {
        if (bindingResult.hasErrors()) {
            return "register";
        }

        try {
            accountClient.create(createUserDto);
            return "redirect:/login";
        } catch (HttpClientErrorException e) {
            model.addAttribute("errorMessage", extractBackendError(e));
            model.addAttribute("user", createUserDto);
            return "register";
        }
    }


    @GetMapping({"/", "/home"})
    public String homePage(Model model) {
        try {
            model.addAttribute("rates", exchangeClient.getRates());
        } catch (Exception e) {
            model.addAttribute("ratesError", "Не удалось загрузить курсы валют");
        }
        return "home";
    }

    @ControllerAdvice
    public static class GlobalExceptionHandler {

        @ExceptionHandler(HttpClientErrorException.class)
        public String handleHttpClientError(HttpClientErrorException e, Model model) {
            model.addAttribute("errorMessage", e.getResponseBodyAsString());
            model.addAttribute("user", new CreateUserDto("",
                    "",
                    "",
                    "",
                    null,
                    ""));
            return "register";
        }
    }

    private String extractBackendError(HttpClientErrorException e) {
        String body = e.getResponseBodyAsString();

        if (body == null || body.isBlank()) {
            return "Ошибка регистрации";
        }

        int detailIndex = body.indexOf("\"detail\":\"");
        if (detailIndex >= 0) {
            int start = detailIndex + 10;
            int end = body.indexOf("\"", start);
            if (end > start) {
                return body.substring(start, end).replace("\\\"", "\"");
            }
        }

        return "Ошибка регистрации";
    }
}