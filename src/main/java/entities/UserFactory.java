package entities;

public class UserFactory {
    /**
     * Creates a new User with the given username and password.
     *
     * @param username : the user's username, must not be null or empty.
     * @param password : the user's password, must not be null or empty.
     * @return a new instance of User
     */
    public User createUser(String username, String password) {
        return new User(username, password);
    }
}
