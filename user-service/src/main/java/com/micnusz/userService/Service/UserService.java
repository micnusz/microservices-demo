package com.micnusz.userService.Service;


import com.micnusz.userService.Model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    private final Map<Long, User> users = new HashMap<>();

    public UserService() {
        users.put(1L, new User(1L, "Jan Kowalski", "jan@email.com"));
        users.put(2L, new User(2L, "Anna Nowak", "anna@email.com"));
        users.put(3L, new User(3L, "Piotr Zielinski", "piotr@email.com"));
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    public User getUserById(Long id) {
        return users.get(id);
    }
}
