package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidUserAlreadyExistsException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();
    private final UserValidator userValidator = new UserValidatorImpl();

    @Override
    public User register(User user) {
        userValidator.validate(user);
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidUserAlreadyExistsException("User already exists");
        }
        return storageDao.add(user);
    }
}
