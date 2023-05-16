package kr.ac.hansung.dodobackend.controller;

import jakarta.validation.Valid;
import kr.ac.hansung.dodobackend.model.User;
import kr.ac.hansung.dodobackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User GetUser(@PathVariable("id") int id)
    {
        System.out.println("find id : " + id);
        User user = userService.GetUserById(id);
        return user;
    }

    @PostMapping
    public ResponseEntity<?> AddUser(@Valid @RequestBody User user)
    {
        System.out.println("Entered user info : " + user);
        userService.AddUser(user);
        return new ResponseEntity<>("{}", HttpStatus.CREATED);
    }

}
