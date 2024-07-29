package com.cos.learnspring2;

import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Parameter;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor

@RequestMapping("/user")
public class UserController {
    private final UserRepository userRepository;
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") Long id) {
        Optional<User> byId = userRepository.findById(id);
        if(byId.isPresent()) {
            return ResponseEntity.ok().body(byId.get());
        }
        return null;
    }

    @PostMapping
    public User postUser(@RequestBody User user) {
        userRepository.save(user);
        return user;
    }
}
