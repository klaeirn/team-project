package use_cases.change_username;

import entities.User;
public interface ChangeUsernameDataAccessInterface {

    /**
     * Checks if the given username exists.
     * @param username the username to look for
     * @return true if a user with the given username exists; false otherwise
     */
    boolean existsByName(String username);

    void save(User user);

    void replace(String username);
}
