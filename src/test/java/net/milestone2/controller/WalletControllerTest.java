package net.milestone2.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.milestone2.Utilities.MyResponse;
import net.milestone2.controller.WalletController;
import net.milestone2.model.Wallet;
import net.milestone2.service.WalletService;
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class WalletControllerTest {
    @Mock
    private WalletService walletService;
    private Wallet wallet;
    private List<Wallet>walletList;
    @InjectMocks
    private WalletController walletController;
    @Autowired
    private MockMvc mockMvc;
    @BeforeEach
    public void setWallet()
    {
        wallet=new Wallet();
        wallet.setWalletId("1");
        wallet.setCurr_balance(20);
        mockMvc= MockMvcBuilders.standaloneSetup(walletController).build();
    }
    @AfterEach
    public void tearWallet()
    {
        wallet=null;
    }
    @Test
    public void createWalletTest()throws Exception
    {
        lenient().when(walletService.CreateWallet(wallet)).thenReturn(wallet);
        mockMvc.perform(post("/wallet/1").contentType(MediaType.APPLICATION_JSON).content(asJsonString(wallet))).andExpect(status().isCreated());
        verify(walletService,times(1)).CreateWallet(wallet);
    }

    @Test
    public void getWalletById() throws Exception
    {
        Mockito.when(walletService.getWalletById(wallet.getWalletId())).thenReturn(Optional.ofNullable(wallet));
        mockMvc.perform(get("/wallet/1").contentType(MediaType.APPLICATION_JSON).content(asJsonString(wallet))).andExpect(status().isOk());
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