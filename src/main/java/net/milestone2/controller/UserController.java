package net.milestone2.controller;

import net.milestone2.DAO.UserRepo;
import net.milestone2.model.User;

import net.milestone2.service.UserService;
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



    @PostMapping
    public String saveUser(@RequestBody User user)
    {
        return userService.saveUser(user);
    }
    @GetMapping("{id}")
    public ResponseEntity<User>getUserById(@PathVariable("id") long userid)
    {
        return new ResponseEntity<User>(userService.getUserById(userid),HttpStatus.OK);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<String>deleteUser(@PathVariable("id") long id)
    {

        userService.deleteUser(id);
        return new ResponseEntity<String>("User deleted Sucessfully",HttpStatus.OK);
    }





}
