package com.kolis1on.testassigment.repository;

import com.kolis1on.testassigment.entity.User;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Data
public class UserRepository {
    private List<User> users = new ArrayList<>();



}
