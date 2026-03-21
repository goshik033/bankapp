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
import ru.kolidgio.bankapp.frontui.client.CashClient;
import ru.kolidgio.bankapp.frontui.client.ExchangeClient;
import ru.kolidgio.bankapp.frontui.client.TransferClient;
import ru.kolidgio.bankapp.frontui.dto.*;

@Controller
@RequiredArgsConstructor
public class PageController {
    private final AccountClient accountClient;
    private final ExchangeClient exchangeClient;
    private final CashClient cashClient;
    private final TransferClient transferClient;

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

            if (!model.containsAttribute("createAccountForm")) {
                model.addAttribute("createAccountForm", new CreateAccountDto(""));
            }
            if (!model.containsAttribute("cashForm")) {
                model.addAttribute("cashForm", new CashOperationDto(null, null));
            }
            if (!model.containsAttribute("ownTransferForm")) {
                model.addAttribute("ownTransferForm", new TransferBetweenOwnAccountsRequestDto(null, null, null));
            }
            if (!model.containsAttribute("otherTransferForm")) {
                model.addAttribute("otherTransferForm", new TransferToAnotherUserRequestDto(null, "", null, null));
            }
        } catch (Exception e) {
            model.addAttribute("userError", "Не удалось загрузить данные пользователя");
        }

        return "home";
    }

    @PostMapping("/transfer/other")
    public String transferToAnotherUser(
            @Valid @ModelAttribute("otherTransferForm") TransferToAnotherUserRequestDto otherTransferForm,
            BindingResult bindingResult,
            Authentication authentication,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            return homePage(model, authentication);
        }

        try {
            String currentLogin = authentication.getName();
            UserDto fromUser = accountClient.getUserByLogin(currentLogin);
            UserDto toUser = accountClient.getUserByLogin(otherTransferForm.toUserLogin());

            transferClient.transferToAnotherUser(fromUser.id(), toUser.id(), otherTransferForm);
            return "redirect:/home";
        } catch (HttpClientErrorException e) {
            model.addAttribute("otherTransferError", extractBackendError(e));
            model.addAttribute("otherTransferForm", otherTransferForm);
            return homePage(model, authentication);
        } catch (Exception e) {
            model.addAttribute("otherTransferError", "Не удалось выполнить перевод другому пользователю");
            model.addAttribute("otherTransferForm", otherTransferForm);
            return homePage(model, authentication);
        }
    }

    @PostMapping("/transfer/own")
    public String transferBetweenOwnAccounts(
            @Valid @ModelAttribute("ownTransferForm") TransferBetweenOwnAccountsRequestDto ownTransferForm,
            BindingResult bindingResult,
            Authentication authentication,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            return homePage(model, authentication);
        }

        if (ownTransferForm.fromAccountId().equals(ownTransferForm.toAccountId())) {
            model.addAttribute("ownTransferError", "Счёт отправления и счёт получения должны отличаться");
            model.addAttribute("ownTransferForm", ownTransferForm);
            return homePage(model, authentication);
        }

        try {
            String login = authentication.getName();
            UserDto user = accountClient.getUserByLogin(login);

            transferClient.transferBetweenOwnAccounts(user.id(), ownTransferForm);
            return "redirect:/home";
        } catch (HttpClientErrorException e) {
            model.addAttribute("ownTransferError", extractBackendError(e));
            model.addAttribute("ownTransferForm", ownTransferForm);
            return homePage(model, authentication);
        } catch (Exception e) {
            model.addAttribute("ownTransferError", "Не удалось выполнить перевод между своими счетами");
            model.addAttribute("ownTransferForm", ownTransferForm);
            return homePage(model, authentication);
        }
    }

    @PostMapping("/accounts/create")
    public String createAccount(@Valid @ModelAttribute("createAccountForm") CreateAccountDto createAccountDto,
                                BindingResult bindingResult,
                                Authentication authentication,
                                Model model) {
        if (bindingResult.hasErrors()) {
            return homePage(model, authentication);
        }

        try {
            String login = authentication.getName();
            UserDto user = accountClient.getUserByLogin(login);

            accountClient.createAccount(user.id(), createAccountDto);
            return "redirect:/home";
        } catch (HttpClientErrorException e) {
            model.addAttribute("accountError", extractBackendError(e));
            model.addAttribute("createAccountForm", createAccountDto);
            return homePage(model, authentication);
        } catch (Exception e) {
            model.addAttribute("accountError", "Не удалось создать счёт");
            model.addAttribute("createAccountForm", createAccountDto);
            return homePage(model, authentication);
        }
    }
    @PostMapping("/cash/deposit")
    public String deposit(@Valid @ModelAttribute("cashForm") CashOperationDto cashForm,
                          BindingResult bindingResult,
                          Authentication authentication,
                          Model model) {
        if (bindingResult.hasErrors()) {
            return homePage(model, authentication);
        }

        try {
            String login = authentication.getName();
            UserDto user = accountClient.getUserByLogin(login);

            cashClient.deposit(user.id(), cashForm);
            return "redirect:/home";
        } catch (HttpClientErrorException e) {
            model.addAttribute("cashError", extractBackendError(e));
            model.addAttribute("cashForm", cashForm);
            return homePage(model, authentication);
        } catch (Exception e) {
            model.addAttribute("cashError", "Не удалось пополнить счёт");
            model.addAttribute("cashForm", cashForm);
            return homePage(model, authentication);
        }
    }

    @PostMapping("/cash/withdraw")
    public String withdraw(@Valid @ModelAttribute("cashForm") CashOperationDto cashForm,
                           BindingResult bindingResult,
                           Authentication authentication,
                           Model model) {
        if (bindingResult.hasErrors()) {
            return homePage(model, authentication);
        }

        try {
            String login = authentication.getName();
            UserDto user = accountClient.getUserByLogin(login);

            cashClient.withdraw(user.id(), cashForm);
            return "redirect:/home";
        } catch (HttpClientErrorException e) {
            model.addAttribute("cashError", extractBackendError(e));
            model.addAttribute("cashForm", cashForm);
            return homePage(model, authentication);
        } catch (Exception e) {
            model.addAttribute("cashError", "Не удалось снять деньги");
            model.addAttribute("cashForm", cashForm);
            return homePage(model, authentication);
        }
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