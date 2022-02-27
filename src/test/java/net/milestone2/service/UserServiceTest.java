package net.milestone2.service;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.milestone2.DAO.UserRepo;
import net.milestone2.model.User;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class UserServiceTest {


    @MockBean
    private UserRepo userRepo;

    @InjectMocks
    private UserService userService;

    ObjectMapper objectMapper = new ObjectMapper();



    @Test
    void createUser() throws IOException {

        String userJson=new String(Files.readAllBytes(Paths.get("src/test/java/rec/UserDetails.json")));

        User user=objectMapper.readValue(userJson, User.class);
        Mockito.when(userRepo.save(user)).thenReturn(user);
        Assert.assertEquals(userService.saveUser(user),"Stored");

    }

    @Test
    void findByMobileno() throws IOException {
        String userJson=new String(Files.readAllBytes(Paths.get("src/test/java/rec/UserDetails.json")));

        User user=objectMapper.readValue(userJson,User.class);
        Mockito.when(userRepo.findByMobileno(user.getMobileno())).thenReturn(user);
        Assert.assertEquals(userService.findByMobileno(user.getMobileno()),user);
//        assertThrows(IOException.class,() -> userService.findByMobileno("20"));
    }

    @Test
    void findByEmail() throws IOException {
        String x="src/test/java/rec/UserDetails.json";
        String userJson=new String(Files.readAllBytes(Paths.get(x)));

        User user=objectMapper.readValue(userJson,User.class);
        Mockito.when(userRepo.findByEmail(user.getEmail())).thenReturn(user);
        Assert.assertEquals(userService.findByEmail(user.getEmail()),user);

    }

    @Test
    void findUserByUsername() throws IOException {
        String userJson=new String(Files.readAllBytes(Paths.get("src/test/java/rec/UserDetails.json")));

        User user=objectMapper.readValue(userJson,User.class);
        Mockito.when(userRepo.findByUsername(user.getUsername())).thenReturn(user);
        Assert.assertEquals(userService.findUserByUsername(user.getUsername()),user);

    }
}