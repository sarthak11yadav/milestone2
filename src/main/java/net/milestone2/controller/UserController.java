package net.milestone2.controller;

import net.milestone2.DAO.UserRepo;
import net.milestone2.model.User;

import net.milestone2.service.UserService;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;


import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {


    @Autowired
    UserService userService;
//
//    @Autowired
//    KafkaTemplate<String,String> kafkaTemplate;
//    private static final String TOPIC = "paytm";

    private static final Logger logger= Logger.getLogger(UserController.class);

//    {
//        BasicConfigurator.configure();
//    }

    @PostMapping
    public String saveUser(@RequestBody User user)
    {
        logger.debug("user created successfully ");

        return userService.saveUser(user);
    }
    @GetMapping("{id}")
    public ResponseEntity<User>getUserById(@PathVariable("id") long userid)
    {
//
        logger.info("user retrieved of" + userid + "userid");
        return new ResponseEntity<User>(userService.getUserById(userid),HttpStatus.OK);


    }


    @DeleteMapping("{id}")
    public ResponseEntity<String>deleteUser(@PathVariable("id") long id)
    {
        logger.debug("user deleted successfully at id" + id);
        userService.deleteUser(id);
        return new ResponseEntity<String>("User deleted Sucessfully",HttpStatus.OK);
    }





}
