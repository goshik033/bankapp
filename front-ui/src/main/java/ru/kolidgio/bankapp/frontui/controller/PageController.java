package ru.kolidgio.bankapp.frontui.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import ru.kolidgio.bankapp.frontui.client.AccountClient;
import ru.kolidgio.bankapp.frontui.client.ExchangeClient;
import ru.kolidgio.bankapp.frontui.dto.CreateUserDto;
import ru.kolidgio.bankapp.frontui.dto.UserDto;

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
        System.out.println("REGISTER HIT");
        System.out.println("DTO = " + createUserDto);

        if (bindingResult.hasErrors()) {
            System.out.println("BINDING ERRORS = " + bindingResult.getAllErrors());
            return "register";
        }

        try {
            System.out.println("CALL ACCOUNTS-SERVICE");
            accountClient.create(createUserDto);
            System.out.println("REGISTER SUCCESS");
            return "redirect:/login";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", e.getMessage());
            return "register";
        }
    }


    @GetMapping({"/", "/home"})
    public String homePage(Model model, Authentication authentication) {
        try {
            model.addAttribute("rates", exchangeClient.getRates());
        } catch (Exception e) {
            model.addAttribute("ratesError", "Не удалось загрузить курсы валют");
        }

        try {
            String login = authentication.getName();
            UserDto user = accountClient.getUserByLogin(login);

            model.addAttribute("currentUser", user);
            model.addAttribute("accounts", accountClient.getAccountsByUserId(user.id()));
        } catch (Exception e) {
            model.addAttribute("userError", "Не удалось загрузить данные пользователя");
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