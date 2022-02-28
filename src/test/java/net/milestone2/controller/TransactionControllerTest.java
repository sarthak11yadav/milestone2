package net.milestone2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.milestone2.service.UserService;
import net.milestone2.service.WalletService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private WalletService walletService;

    //Done: how can pass long value in requesturl.
    @Test
    void getStatusByTxnId() throws Exception {

        Long txnId=16L;
        MvcResult result=  mockMvc.perform(MockMvcRequestBuilders.get("/transaction/1").param("txnId",String.valueOf(txnId)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String resultJson=result.getResponse().getContentAsString();
        Assert.assertEquals(resultJson,"Success");
    }


}