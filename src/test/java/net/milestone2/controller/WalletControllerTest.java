package net.milestone2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.milestone2.DTO.JwtRequest;
import net.milestone2.DTO.JwtResponse;
import net.milestone2.Utilities.MyResponse;
import net.milestone2.model.User;
import net.milestone2.model.Wallet;
import net.milestone2.service.UserService;
import net.milestone2.service.WalletService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@SpringBootTest
@AutoConfigureMockMvc

class WalletControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private WalletService walletService;

    // for generate token .
    public  String  GenerateMockMvcToken(String userWalletId) throws Exception {
        User user=userService.findByMobileno(userWalletId);

        String username=user.getUsername();
        String password=user.getPassword();

        JwtRequest jwtRequest=new JwtRequest(username,password);

        String requestTokenJson =objectMapper.writeValueAsString(jwtRequest);

        MvcResult result= mockMvc.perform(MockMvcRequestBuilders.post("/token")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestTokenJson))
                .andExpect( MockMvcResultMatchers.status().isOk())
                .andReturn();
        String resultContent=result.getResponse().getContentAsString();
        JwtResponse token=objectMapper.readValue(resultContent,JwtResponse.class);

        String userToken =token.getToken();

        return userToken;
    }



    @Test
    void createWallet() throws Exception {
        String mobileNumber="2";

        Assert.assertThrows(NullPointerException.class,() -> GenerateMockMvcToken("8"));

        String userToken = GenerateMockMvcToken(mobileNumber);


        MvcResult result= mockMvc.perform(MockMvcRequestBuilders.post("/wallet/{mobileNumber}",mobileNumber)
                        .header(AUTHORIZATION,"Bearer "+userToken))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String response =result.getResponse().getContentAsString();

        MyResponse msg =objectMapper.readValue(response, MyResponse.class);

        // Response class .
        String walletResJson=new String(Files.readAllBytes(Paths.get("src/test/java/rec/CreateWalletRes.json")));
        MyResponse resMsg =objectMapper.readValue(walletResJson, MyResponse.class);

        Assert.assertEquals( resMsg.getMsg(),msg.getMsg());
        Assert.assertEquals(resMsg.getStatus(),msg.getStatus());


//        walletService.DeleteWalletById(mobileNumber);


    }
    @Test
    void addMoney() throws Exception {

        String mobileNumber="2";
        Long amount=10L;
        //generate token
        Assert.assertThrows(NullPointerException.class,() -> GenerateMockMvcToken("8"));
        String userToken = GenerateMockMvcToken(mobileNumber);

        final String requestUrl ="/wallet/"+mobileNumber+"/"+String.valueOf(amount);
        MvcResult result= mockMvc.perform(MockMvcRequestBuilders.post(requestUrl)
                        .header(AUTHORIZATION,"Bearer "+userToken))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String response =result.getResponse().getContentAsString();
        MyResponse msg =objectMapper.readValue(response, MyResponse.class);


        String walletResJson=new String(Files.readAllBytes(Paths.get("src/test/java/rec/AddMoneyRes.json")));
        MyResponse resMsg =objectMapper.readValue(walletResJson, MyResponse.class);


        Assert.assertEquals(resMsg.getMsg() ,msg.getMsg());
        Assert.assertEquals(resMsg.getStatus(),msg.getStatus());


        Wallet w =walletService.getWalletById(mobileNumber).orElseThrow(()-> new RuntimeException("Invalid ID"));
        w.setCurr_balance(w.getCurr_balance()-amount);
        walletService.updateWallet(w);


    }

}