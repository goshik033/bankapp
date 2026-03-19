package ru.kolidgio.bankapp.transfer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kolidgio.bankapp.transfer.client.AccountClient;
import ru.kolidgio.bankapp.transfer.client.BlockerClient;
import ru.kolidgio.bankapp.transfer.client.NotificationClient;
import ru.kolidgio.bankapp.transfer.dto.account.AccountDto;
import ru.kolidgio.bankapp.transfer.dto.blocker.BlockerResponseDto;
import ru.kolidgio.bankapp.transfer.dto.notification.NotificationRequestDto;
import ru.kolidgio.bankapp.transfer.dto.transfer.TransferBetweenOwnAccountsRequestDto;
import ru.kolidgio.bankapp.transfer.dto.transfer.TransferResultDto;
import ru.kolidgio.bankapp.transfer.dto.transfer.TransferToAnotherUserRequestDto;
import ru.kolidgio.bankapp.transfer.service.errors.OperationBlockedException;

@Service
@RequiredArgsConstructor
public class TransferService {

    private final NotificationClient notificationClient;
    private final BlockerClient blockerClient;
    private final AccountClient accountClient;

    public TransferResultDto transferBetweenOwnAccounts(Long userId,
                                                        Long fromAccountId,
                                                        TransferBetweenOwnAccountsRequestDto dto) {
        checkBlocked(dto.amount());

        AccountDto fromAccount = accountClient.findById(userId, fromAccountId);
        AccountDto toAccount = accountClient.findById(userId, dto.toAccountId());

        validateSameCurrency(fromAccount, toAccount);

        accountClient.withdraw(userId, fromAccountId, dto.amount());
        accountClient.deposit(userId, dto.toAccountId(), dto.amount());

        sendTransferNotifications(userId, fromAccountId, userId, dto.toAccountId(), dto.amount());

        return new TransferResultDto(
                fromAccountId,
                dto.toAccountId(),
                dto.amount(),
                "SUCCESS"
        );
    }

    public TransferResultDto transferToAnotherUser(Long fromUserId,
                                                   Long fromAccountId,
                                                   TransferToAnotherUserRequestDto dto) {
        checkBlocked(dto.amount());

        AccountDto fromAccount = accountClient.findById(fromUserId, fromAccountId);
        AccountDto toAccount = accountClient.findById(dto.toUserId(), dto.toAccountId());

        validateSameCurrency(fromAccount, toAccount);

        accountClient.withdraw(fromUserId, fromAccountId, dto.amount());
        accountClient.deposit(dto.toUserId(), dto.toAccountId(), dto.amount());

        sendTransferNotifications(fromUserId, fromAccountId, dto.toUserId(), dto.toAccountId(), dto.amount());

        return new TransferResultDto(
                fromAccountId,
                dto.toAccountId(),
                dto.amount(),
                "SUCCESS"
        );
    }

    private void checkBlocked(java.math.BigDecimal amount) {
        BlockerResponseDto blockerResponseDto = blockerClient.check(amount);
        if (blockerResponseDto.blocked()) {
            throw new OperationBlockedException(blockerResponseDto.reason());
        }
    }

    private void validateSameCurrency(AccountDto fromAccount, AccountDto toAccount) {
        if (!fromAccount.currency().equals(toAccount.currency())) {
            throw new IllegalArgumentException("Для текущей версии transfer-service счета должны быть в одной валюте");
        }
    }

    private void sendTransferNotifications(Long fromUserId,
                                           Long fromAccountId,
                                           Long toUserId,
                                           Long toAccountId,
                                           java.math.BigDecimal amount) {
        notificationClient.send(new NotificationRequestDto(
                fromUserId,
                fromAccountId,
                "TRANSFER_OUT",
                amount,
                "SUCCESS"
        ));

        notificationClient.send(new NotificationRequestDto(
                toUserId,
                toAccountId,
                "TRANSFER_IN",
                amount,
                "SUCCESS"
        ));
    }
}