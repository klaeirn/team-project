package Entities;

import java.util.List;

public class UserFactory {

    public User createUser(String user_name, String password)
    {
        return new User(user_name, password);
    }
}
