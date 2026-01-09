package com.alepaucar.expense_tracker.controller;

import com.alepaucar.expense_tracker.DTO.CategoryResDTO;
import com.alepaucar.expense_tracker.DTO.UserReqDTO;
import com.alepaucar.expense_tracker.DTO.UserResDTO;
import com.alepaucar.expense_tracker.services.UserApiService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserApiController {
    private final UserApiService userApiService;

    public UserApiController(UserApiService userApiService) {
        this.userApiService = userApiService;
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResDTO> getUser(@PathVariable Long userId){
        UserResDTO user = userApiService.getUserFromId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResDTO>> getAllUser(){
        List<UserResDTO> users = userApiService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @PostMapping("/users")
    public ResponseEntity<UserResDTO> createNewUser(@RequestBody UserReqDTO userReqDTO){
        UserResDTO user = userApiService.addNewUser(userReqDTO);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserResDTO> updateUserFromId(@PathVariable Long userId, @RequestBody UserReqDTO userReqDTO){
        UserResDTO user = userApiService.updateExistingUser(userId, userReqDTO);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId){
        userApiService.deleteUserFromId(userId);
        return ResponseEntity.noContent().build();
    }
}
