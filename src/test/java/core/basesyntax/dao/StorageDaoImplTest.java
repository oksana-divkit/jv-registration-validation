package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageDaoImplTest {
    private static StorageDao storage;
    private static User user;
    private static User user2;

    @BeforeAll
    public static void setUp() {
        storage = new StorageDaoImpl();
        user = new User();
        user2 = new User();
    }

    @BeforeEach
    public void each() {
        storage.clear();

        user.setId(null);
        user.setLogin("123456");
        user.setAge(18);
        user.setPassword("123456");

        user2.setId(null);
        user2.setLogin("12345678");
        user2.setAge(36);
        user2.setPassword("12345678");
    }

    @Test
    void add_returnUser_Ok() {
        assertEquals(user, storage.add(user));
        assertEquals(user2, storage.add(user2));
    }

    @Test
    void add_setId_Ok() {
        storage.add(user);
        assertEquals(1, user.getId());
        storage.add(user2);
        assertEquals(2, user2.getId());
    }

    @Test
    void add_nullUser_notOk() {
        assertThrows(NullPointerException.class, () -> storage.add(null));
    }

    @Test
    void get_existUser_Ok() {
        storage.add(user);
        storage.add(user2);
        assertEquals(user, storage.get(user.getLogin()));
        assertEquals(user2, storage.get(user2.getLogin()));
    }

    @Test
    void get_nonExistUser_null() {
        assertNull(storage.get(user.getLogin()));
        storage.add(user);
        assertNull(storage.get(user2.getLogin()));
    }
}
