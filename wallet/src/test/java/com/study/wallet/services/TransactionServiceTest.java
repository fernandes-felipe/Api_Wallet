package com.study.wallet.services;

import com.study.wallet.domain.user.User;
import com.study.wallet.domain.user.UserType;
import com.study.wallet.dtos.TransactionDTO;
import com.study.wallet.repositories.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AuthorizationService authorizationService;

    @Mock
    private NotificationService notificationService;

    @Autowired
    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Should create transaction successfully when everything is OK")
    void createTransactionSuccess() throws Exception {
        User sender = new User(1L, "Jamie", "Jones", "99999999901", "jamie@email.com", "12345", new BigDecimal(10) , UserType.COMMON);
        User receiver = new User(1L, "Damian", "Lazarus", "99999999902", "damian@gmail.com", "12345", new BigDecimal(10) , UserType.COMMON);

        when(userService.findUserById(1L)).thenReturn(sender);
        when(userService.findUserById(2L)).thenReturn(receiver);

        when(authorizationService.authorizeTransaction(any(), any())).thenReturn(true);

        TransactionDTO request = new TransactionDTO(new BigDecimal(10), 1L, 2L);
        transactionService.createTransaction(request);

        verify(transactionRepository, times(1)).save(any());

        sender.setBalance(new BigDecimal(0));
        verify(userService, times(1)).saveUser(sender);

        receiver.setBalance(new BigDecimal(20));
        verify(userService, times(1)).saveUser(receiver);

//        verify(notificationService, times(1)).sendNotification(sender, "Sent Notification");
//        verify(notificationService, times(1)).sendNotification(receiver, "Transação recebida com sucesso");
    }

    @Test
    @DisplayName("Should throw exception whe transaction is not allowed")
    void createTransactionWhenAuthorizationFailed() throws Exception {
        User sender = new User(1L, "Jamie", "Jones", "99999999901", "jamie@email.com", "12345", new BigDecimal(10) , UserType.COMMON);
        User receiver = new User(1L, "Damian", "Lazarus", "99999999902", "damian@gmail.com", "12345", new BigDecimal(10) , UserType.COMMON);

        when(userService.findUserById(1L)).thenReturn(sender);
        when(userService.findUserById(2L)).thenReturn(receiver);

        when(authorizationService.authorizeTransaction(any(), any())).thenReturn(false);

        Exception thrown = Assertions.assertThrows(Exception.class, () -> {
            TransactionDTO request = new TransactionDTO(new BigDecimal(10), 1L, 2L);
            transactionService.createTransaction(request);
        });

        Assertions.assertEquals("Transaction not authorized", thrown.getMessage());
    }
}