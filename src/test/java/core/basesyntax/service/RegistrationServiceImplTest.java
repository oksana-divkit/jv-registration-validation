package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidUserAgeException;
import core.basesyntax.exception.InvalidUserLoginException;
import core.basesyntax.exception.InvalidUserPasswordException;
import core.basesyntax.exception.UserAlreadyExistsException;
import core.basesyntax.exception.UserValidationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int MIN_LENGTH_LOGIN = 6;
    private static final int MIN_LENGTH_PASSWORD = 6;
    private static final int MIN_AGE = 18;

    private static RegistrationService registrationService = new RegistrationServiceImpl();
    private static User user = new User();

    @BeforeEach
    public void each() {
        Storage.people.clear();
        user.setLogin("123456");
        user.setAge(18);
        user.setPassword("123456");
    }

    @Test
    void register_validUser_Ok() {
        assertEquals(user, registrationService.register(user));

        User user2 = new User();
        user2.setLogin("12345678");
        user2.setAge(36);
        user2.setPassword("12345678");
        assertEquals(user2, registrationService.register(user2));
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(UserValidationException.class,
                () -> registrationService.register(null)
        );
    }

    @Test
    void register_existUser_notOk() {
        registrationService.register(user);
        UserAlreadyExistsException existsException = assertThrows(
                UserAlreadyExistsException.class,
                () -> registrationService.register(user)
        );
        assertEquals("User " + user.getLogin() + " already exists",
                existsException.getMessage());
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        InvalidUserLoginException loginException = assertThrows(
                InvalidUserLoginException.class,
                () -> registrationService.register(user)
        );
        assertEquals(
                "Invalid login, must be at least " + MIN_LENGTH_LOGIN + " characters long",
                loginException.getMessage()
        );
    }

    @Test
    void register_shortLogin_notOk() {
        user.setLogin("");
        InvalidUserLoginException loginException = assertThrows(
                InvalidUserLoginException.class,
                () -> registrationService.register(user)
        );
        assertEquals(
                "Invalid login, must be at least " + MIN_LENGTH_LOGIN + " characters long",
                loginException.getMessage()
        );

        user.setLogin("123");
        loginException = assertThrows(
                InvalidUserLoginException.class,
                () -> registrationService.register(user)
        );
        assertEquals(
                "Invalid login, must be at least " + MIN_LENGTH_LOGIN + " characters long",
                loginException.getMessage()
        );

        user.setLogin("12345");
        loginException = assertThrows(
                InvalidUserLoginException.class,
                () -> registrationService.register(user)
        );
        assertEquals(
                "Invalid login, must be at least " + MIN_LENGTH_LOGIN + " characters long",
                loginException.getMessage()
        );
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        InvalidUserAgeException ageException = assertThrows(
                InvalidUserAgeException.class,
                () -> registrationService.register(user)
        );
        assertEquals(
                "Invalid age, should be over " + MIN_AGE,
                ageException.getMessage()
        );
    }

    @Test
    void register_childAge_notOk() {
        user.setAge(17);
        InvalidUserAgeException ageException = assertThrows(
                InvalidUserAgeException.class,
                () -> registrationService.register(user)
        );
        assertEquals(
                "Invalid age, should be over " + MIN_AGE,
                ageException.getMessage()
        );
    }

    @Test
    void register_negativeAge_notOk() {
        user.setAge(-17);
        InvalidUserAgeException ageException = assertThrows(
                InvalidUserAgeException.class,
                () -> registrationService.register(user)
        );
        assertEquals(
                "Invalid age, should be over " + MIN_AGE,
                ageException.getMessage()
        );
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        InvalidUserPasswordException passwordException = assertThrows(
                InvalidUserPasswordException.class,
                () -> registrationService.register(user)
        );
        assertEquals(
                "Invalid password, must be at least " + MIN_LENGTH_PASSWORD + " characters long",
                passwordException.getMessage()
        );
    }

    @Test
    void register_shortPassword_notOk() {
        user.setPassword("");
        InvalidUserPasswordException passwordException = assertThrows(
                InvalidUserPasswordException.class,
                () -> registrationService.register(user)
        );
        assertEquals(
                "Invalid password, must be at least " + MIN_LENGTH_PASSWORD + " characters long",
                passwordException.getMessage()
        );

        user.setPassword("123");
        passwordException = assertThrows(
                InvalidUserPasswordException.class,
                () -> registrationService.register(user)
        );
        assertEquals(
                "Invalid password, must be at least " + MIN_LENGTH_PASSWORD + " characters long",
                passwordException.getMessage()
        );

        user.setPassword("12345");
        passwordException = assertThrows(
                InvalidUserPasswordException.class,
                () -> registrationService.register(user)
        );
        assertEquals(
                "Invalid password, must be at least " + MIN_LENGTH_PASSWORD + " characters long",
                passwordException.getMessage()
        );
    }
}
