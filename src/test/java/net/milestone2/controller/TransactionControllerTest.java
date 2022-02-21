package net.milestone2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.milestone2.model.Transaction;
import net.milestone2.service.TransactionService;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc

public class TransactionControllerTest {

    @Mock
    private TransactionService transactionService;

    private Transaction transaction;

    private List<Transaction> transactionList;

    @InjectMocks
    private TransactionController transactionController;
    @Autowired
    private MockMvc mockMvc;
    @BeforeEach
    public void setTransaction()
    {
        long x=1;
        transaction=new Transaction();
        transaction.setTxnId(x);
        transaction.setUserNumber("1");
        transaction.setStatus("success");
        transaction.setPayeeWalletId("1");
        transaction.setPayerWalletId("2");
        transaction.setAmount(100);

        mockMvc= MockMvcBuilders.standaloneSetup(transactionController).build();
    }
    @AfterEach
    public void tearWallet()
    {
        transaction=null;
    }
    @Test
    public void getTransactionById()throws Exception
    {
        Mockito.when(transactionService.getTxnByById(transaction.getTxnId())).thenReturn(transaction);

        mockMvc.perform(MockMvcRequestBuilders.get("/transaction/1").contentType(MediaType.APPLICATION_JSON).content(asJsonString(transaction))).andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
    }
    public static String asJsonString(final Object obj)
    {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
