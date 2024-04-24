package com.kolis1on.testassigment.controller;

import com.kolis1on.testassigment.dto.UpdateUserDTO;
import com.kolis1on.testassigment.entity.User;
import com.kolis1on.testassigment.service.UserService;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.List;

@RestController
@RequestMapping("/api/user/")
@AllArgsConstructor

public class UserController {

    private UserService userService;

    @PostMapping("create")
    public HttpStatus createUser(@RequestBody User user) throws Exception {
        userService.createUser(user);
        return HttpStatus.OK;
    }

    @PutMapping("update")
    public HttpStatus updateUser(@RequestBody UpdateUserDTO updateUserDTO){
        userService.updateUser(updateUserDTO.getEmail(), updateUserDTO.getUser());

        return HttpStatus.OK;
    }


    @DeleteMapping("delete")
    public HttpStatus deleteUser(@RequestBody String email){
        userService.deleteUserByEmail(email);
        return HttpStatus.OK;
    }

    @GetMapping("find")
    public List<User> findByBirthRange(@RequestParam(name = "from") String from,
                                       @RequestParam(name = "to") String to) throws Exception {

        return userService.findByBirthRange(from, to);

    }




}
