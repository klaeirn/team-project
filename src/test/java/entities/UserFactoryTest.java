package entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Basic unit tests for UserFactory.
 */
public class UserFactoryTest {

    @Test
    public void testCreateUser() {
        UserFactory factory = new UserFactory();
        User user = factory.createUser("testUser", "password123");

        assertNotNull(user);
        assertEquals("testUser", user.getUserName());
        assertEquals("password123", user.getPassword());
    }

    @Test
    public void testCreateMultipleUsers() {
        UserFactory factory = new UserFactory();
        User user1 = factory.createUser("user1", "pass1");
        User user2 = factory.createUser("user2", "pass2");

        assertNotSame(user1, user2);
        assertEquals("user1", user1.getUserName());
        assertEquals("user2", user2.getUserName());
    }

    @Test
    public void testCreateUserWithEmptyStrings() {
        UserFactory factory = new UserFactory();
        User user = factory.createUser("", "");

        assertNotNull(user);
        assertEquals("", user.getUserName());
        assertEquals("", user.getPassword());
    }
}

