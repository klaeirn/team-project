package entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Basic unit tests for User entity.
 */
public class UserTest {

    @Test
    public void testUserCreation() {
        User user = new User("testUser", "password123");

        assertEquals("testUser", user.getUserName());
        assertEquals("password123", user.getPassword());
    }

    @Test
    public void testSetUserName() {
        User user = new User("originalName", "password123");
        user.setUserName("newName");

        assertEquals("newName", user.getUserName());
    }

    @Test
    public void testGetPassword() {
        User user = new User("testUser", "securePassword");

        assertEquals("securePassword", user.getPassword());
    }

    @Test
    public void testEmptyUserName() {
        User user = new User("", "password");

        assertEquals("", user.getUserName());
    }

    @Test
    public void testEmptyPassword() {
        User user = new User("testUser", "");

        assertEquals("", user.getPassword());
    }
}

