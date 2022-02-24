package net.milestone2.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import net.milestone2.DTO.JwtRequest;
import net.milestone2.DTO.JwtResponse;
import net.milestone2.Utilities.MyResponse;
import net.milestone2.exception.BadRequestException;
import net.milestone2.model.User;
import net.milestone2.service.UserService;
import org.json.JSONObject;
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

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {



    @Autowired
    MockMvc mockMvc;


    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserService userService;

    // for generate token .
    public  String  GenerateMockMvcToken(User user) throws Exception {
        // user -->username and password .
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



    //Done work Perfectly
    @Test
    void createSuccessfulUser() throws Exception {

        String requestJson =new String(Files.readAllBytes(Paths.get("src/test/java/rec/UserCreateReq.json")));
        String ExpectedOutputJson = new String(Files.readAllBytes(Paths.get("src/test/java/rec/UserCreateRes.json")));

        MvcResult result= mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String resultContent=result.getResponse().getContentAsString();

        Assert.assertEquals("Stored",resultContent) ;


    }

    @Test
    void deleteUser() throws Exception {
        Long userId=9L;
        //Store already user so we can back again data .
        User user=userService.getUserById(userId);

        //generate token
        String userToken = GenerateMockMvcToken(user);

        MvcResult result= mockMvc.perform(MockMvcRequestBuilders.delete("/users/9")
                        .header(AUTHORIZATION,"Bearer "+userToken))
                .andExpect( MockMvcResultMatchers.status().isOk())
                .andReturn();

        String resultContent=result.getResponse().getContentAsString();

        Assert.assertEquals("User Deleted sucessfully",resultContent) ;


        userService.updateUser(user);

    }



}