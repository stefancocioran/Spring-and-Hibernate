package com.luxoft.bankapp;

import com.luxoft.bankapp.exceptions.NotEnoughFundsException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.CheckingAccount;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.Banking;
import com.luxoft.bankapp.service.audit.AuditService;
import com.luxoft.bankapp.service.audit.WithdrawState;
import com.luxoft.bankapp.service.operations.BankingOperationsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

/*
@SpringBootTest(classes = BankApplication.class)
public class BankApplicationTests4 {
    @Autowired
    private Banking banking;

    @Autowired
    private BankingOperationsService bankingOperationsService;

    @SpyBean
    private AuditService auditService;

    private Client client;

    @BeforeEach
    public void init() {
        client = banking.getClient("Jonny Bravo");
        client.setDefaultActiveAccountIfNotSet();
        client.getActiveAccount().setId(999);
    }

    @Test
    public void depositToClient1() {
        double amount = 100;

        bankingOperationsService.deposit(client, amount);

        verify(auditService, times(1))
                .auditDeposit(client.getActiveAccount().getId(), amount);
    }

    @Test
    public void depositToClient2() {
        Account account = client.getActiveAccount();
        double amount = 100;

        bankingOperationsService.deposit(account, amount);

        verify(auditService, times(1))
                .auditDeposit(account.getId(), amount);
    }

    @Test
    public void getClientBalance() {
        bankingOperationsService.getBalance(client);

        verify(auditService, times(1))
                .auditBalance(client.getActiveAccount().getId());

    }

    @Test
    public void withdrawFromClient1() {
        double amount = 100;

        bankingOperationsService.withdraw(client, amount);

        verify(auditService, times(1))
                .auditWithdraw(client.getActiveAccount().getId(), amount, WithdrawState.TRYING);

        verify(auditService, times(1))
                .auditWithdraw(client.getActiveAccount().getId(), amount, WithdrawState.SUCCESSFUL);
    }

    @Test
    public void withdrawFromClient2() {
        Account account = client.getActiveAccount();
        double amount = 100;

        bankingOperationsService.withdraw(account, amount);

        verify(auditService, times(1))
                .auditWithdraw(client.getActiveAccount().getId(), amount, WithdrawState.TRYING);

        verify(auditService, times(1))
                .auditWithdraw(client.getActiveAccount().getId(), amount, WithdrawState.SUCCESSFUL);
    }

    @Test
    public void withdrawFromClient3() {
        Account account = client.getActiveAccount();
        double balance = account.getBalance();
        double overdraft = 0;

        if (account instanceof CheckingAccount) {
            overdraft = ((CheckingAccount) account).getOverdraft();
        }

        double amount = balance + overdraft + 1000;

        assertThrows(NotEnoughFundsException.class,
                () -> bankingOperationsService.withdraw(account, amount));

        verify(auditService, times(1))
                .auditWithdraw(client.getActiveAccount().getId(), amount, WithdrawState.TRYING);

        verify(auditService, times(1))
                .auditWithdraw(client.getActiveAccount().getId(), amount, WithdrawState.FAILED);
    }
}
 */

// TASK 3.11: Fix tests after implementing the AuditService using events.
@SpringBootTest(classes = BankApplication.class)
public class BankApplicationTests4 {
    @Autowired
    private Banking banking;

    @Autowired
    private BankingOperationsService bankingOperationsService;

    @Autowired
    private AuditService auditService;

    private Client client;

    @BeforeEach
    public void init() {
        client = banking.getClient("Jonny Bravo");
        client.setDefaultActiveAccountIfNotSet();
        client.getActiveAccount().setId(999);
    }

    @Test
    public void depositToClient1() {
        double amount = 100;

        int countOfEvents = auditService.getEvents().size();

        bankingOperationsService.deposit(client, amount);

        assertEquals(countOfEvents + 1, auditService.getEvents().size());
    }

    @Test
    public void depositToClient1ConditionFailed() {
        double amount = 98;

        int countOfEvents = auditService.getEvents().size();

        bankingOperationsService.deposit(client, amount);

        assertEquals(countOfEvents, auditService.getEvents().size());
    }

    @Test
    public void depositToClient2() {
        Account account = client.getActiveAccount();
        double amount = 100;

        int countOfEvents = auditService.getEvents().size();

        bankingOperationsService.deposit(account, amount);

        assertEquals(countOfEvents + 1, auditService.getEvents().size());
    }

    @Test
    public void getClientBalance() {
        int countOfEvents = auditService.getEvents().size();

        bankingOperationsService.getBalance(client);

        assertEquals(countOfEvents + 1, auditService.getEvents().size());
    }

    @Test
    public void withdrawFromClient1() {
        double amount = 100;

        int countOfEvents = auditService.getEvents().size();

        bankingOperationsService.withdraw(client, amount);

        assertEquals(countOfEvents + 2, auditService.getEvents().size());
    }

    @Test
    public void withdrawFromClient2() {
        Account account = client.getActiveAccount();
        double amount = 100;

        int countOfEvents = auditService.getEvents().size();

        bankingOperationsService.withdraw(account, amount);

        assertEquals(countOfEvents + 2, auditService.getEvents().size());
    }

    @Test
    public void withdrawFromClient3() {
        Account account = client.getActiveAccount();
        double balance = account.getBalance();
        double overdraft = 0;

        if (account instanceof CheckingAccount) {
            overdraft = ((CheckingAccount) account).getOverdraft();
        }

        double amount = balance + overdraft + 1000;

        int countOfEvents = auditService.getEvents().size();

        assertThrows(NotEnoughFundsException.class,
                () -> bankingOperationsService.withdraw(account, amount));

        assertEquals(auditService.getEvents().size(), countOfEvents + 2);
    }

}

