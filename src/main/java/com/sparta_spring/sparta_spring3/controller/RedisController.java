package com.sparta_spring.sparta_spring3.controller;

import com.sparta_spring.sparta_spring3.domain.User;
import com.sparta_spring.sparta_spring3.repository.RedisRepository;
import org.springframework.web.bind.annotation.*;

@RestController
public class RedisController {

    private final RedisRepository redisRepository;
    int cnt = 0;

    public RedisController(RedisRepository redisRepository) {
        this.redisRepository = redisRepository;
    }

    @GetMapping("/redis/C")
    public User saveUser() {
        cnt++;
        return redisRepository.save(
                new User(
                        "username" + cnt,
                        "password" + cnt,
                        "email" + cnt
                )
        );
    }

    @GetMapping("redis/R")
    public Iterable<User> getAllUser() {
        return redisRepository.findAll();
    }
}
