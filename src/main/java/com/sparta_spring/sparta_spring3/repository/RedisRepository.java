package com.sparta_spring.sparta_spring3.repository;

import com.sparta_spring.sparta_spring3.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface RedisRepository extends CrudRepository<User, Long> {
}
