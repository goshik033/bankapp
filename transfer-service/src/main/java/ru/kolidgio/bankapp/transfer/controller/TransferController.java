package ru.kolidgio.bankapp.transfer.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.kolidgio.bankapp.transfer.dto.transfer.TransferBetweenOwnAccountsRequestDto;
import ru.kolidgio.bankapp.transfer.dto.transfer.TransferResultDto;
import ru.kolidgio.bankapp.transfer.dto.transfer.TransferToAnotherUserRequestDto;
import ru.kolidgio.bankapp.transfer.service.TransferService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/{userId}/accounts/{fromAccountId}/transfer")
public class TransferController {
    private final TransferService transferService;

    @PostMapping("/own")
    public TransferResultDto transferBetweenOwnAccounts(@PathVariable("userId") Long userId,
                                                        @PathVariable("fromAccountId") Long fromAccountId,
                                                        @RequestBody @Valid TransferBetweenOwnAccountsRequestDto dto) {
        return transferService.transferBetweenOwnAccounts(userId, fromAccountId, dto);
    }
    @PostMapping("/other")
    public TransferResultDto transferToAnotherUser(
            @PathVariable("userId") Long userId,
            @PathVariable("fromAccountId") Long fromAccountId,
            @RequestBody @Valid TransferToAnotherUserRequestDto dto
    ) {
        return transferService.transferToAnotherUser(userId, fromAccountId, dto);
    }


}
