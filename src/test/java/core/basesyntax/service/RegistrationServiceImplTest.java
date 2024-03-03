package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidUserAgeException;
import core.basesyntax.exception.InvalidUserAlreadyExistsException;
import core.basesyntax.exception.InvalidUserException;
import core.basesyntax.exception.InvalidUserLoginException;
import core.basesyntax.exception.InvalidUserPasswordException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static User user;

    @BeforeAll
    public static void setUp() {
        registrationService = new RegistrationServiceImpl();
        user = new User();
    }

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
        assertThrows(InvalidUserException.class,
                () -> registrationService.register(null)
        );
    }

    @Test
    void register_existUser_notOk() {
        Storage.people.add(user);
        assertThrows(InvalidUserAlreadyExistsException.class,
                () -> registrationService.register(user)
        );
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(InvalidUserLoginException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortLogin_notOk() {
        user.setLogin("");
        assertThrows(InvalidUserLoginException.class, () -> registrationService.register(user));
        user.setLogin("123");
        assertThrows(InvalidUserLoginException.class, () -> registrationService.register(user));
        user.setLogin("12345");
        assertThrows(InvalidUserLoginException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(InvalidUserAgeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_childAge_notOk() {
        user.setAge(17);
        assertThrows(InvalidUserAgeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_negativeAge_notOk() {
        user.setAge(-17);
        assertThrows(InvalidUserAgeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(InvalidUserPasswordException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortPassword_notOk() {
        user.setPassword("");
        assertThrows(InvalidUserPasswordException.class, () -> registrationService.register(user));
        user.setPassword("123");
        assertThrows(InvalidUserPasswordException.class, () -> registrationService.register(user));
        user.setPassword("12345");
        assertThrows(InvalidUserPasswordException.class, () -> registrationService.register(user));
    }
}
