package com.micnusz.userService.Service;


import com.micnusz.userService.Model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final List<User> usersDataList = new ArrayList<>(List.of(
            new User("Jan Kowalski", "jan.kowalski@email.com"),
            new User("Anna Nowak", "anna.nowak@email.com"),
            new User("Piotr Zielinski", "piotr.zielinski@email.com")
    ));

    public List<User> getAllUsers() {
        return usersDataList;
    }
}
