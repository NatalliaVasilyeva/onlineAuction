package by.minsk.vasilyevanatali.auction.service;


import by.minsk.vasilyevanatali.auction.connection.exception.ConnectionPoolException;
import by.minsk.vasilyevanatali.auction.dao.DaoFactory;
import by.minsk.vasilyevanatali.auction.dao.exception.DaoException;
import by.minsk.vasilyevanatali.auction.dao.impl.UserDao;
import by.minsk.vasilyevanatali.auction.entity.Role;
import by.minsk.vasilyevanatali.auction.entity.User;
import by.minsk.vasilyevanatali.auction.service.exception.ServiceException;
import by.minsk.vasilyevanatali.auction.util.UserValidation;
import by.minsk.vasilyevanatali.auction.util.builder.UserBuilder;
import by.minsk.vasilyevanatali.auction.util.hasher.PasswordHashKeeper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserService {
    private static final Logger LOGGER = LogManager.getLogger(UserService.class);
  //  private DaoFactory daoFactory = DaoFactory.getInstance();

    UserBuilder userBuilder = new UserBuilder();

    public Optional<User> findUserByLoginPassword(String username, String password) throws ServiceException {
        LOGGER.debug("Service looking for user by username and password");
        DaoFactory daoFactory = DaoFactory.getInstance();
        Optional<User> user = Optional.empty();
        UserDao userDao = daoFactory.getUserDao();
        try {
            String hash = PasswordHashKeeper.getInstance().generateHash(username, password);
            user = userDao.findUserByLoginAndPassword(username, hash);
            LOGGER.debug("User " + (user.isPresent() ? user.get() : "not found"));
        } catch (DaoException | SQLException | ConnectionPoolException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return user;
    }

    public Optional<User> findUserByLogin(String username) throws ServiceException {
        LOGGER.debug("Service looking for user by username");
        DaoFactory daoFactory = DaoFactory.getInstance();
        Optional<User> user = Optional.empty();
        UserDao userDao = daoFactory.getUserDao();
        try {
        user = userDao.findUserByLogin(username);
            LOGGER.debug("User " + (user.isPresent() ? user.get() : "not found"));
        } catch (DaoException | SQLException | ConnectionPoolException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return user;
    }


    public boolean findUserByLoginReturnUser(String username) throws ServiceException {
        LOGGER.debug("Service looking for user by username");
        DaoFactory daoFactory = DaoFactory.getInstance();
       User user ;
       boolean result;
        UserDao userDao = daoFactory.getUserDao();
        try {
            System.out.println("im here");
             result = userDao.findUserByLoginReturnUser(username);
           // user = userDao.findUserByLoginReturnUser(username);
 //           LOGGER.debug("User " + (user.isPresent() ? user.get() : "not found"));
        } catch (DaoException | SQLException | ConnectionPoolException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return result;
      //  return user;
    }


    public Optional<User> findUserById(int userId) throws ServiceException {
        LOGGER.debug("Service looking for user by user id");
        DaoFactory daoFactory = DaoFactory.getInstance();
        Optional<User> user = Optional.empty();
        UserDao userDao = daoFactory.getUserDao();
        try {
            user = userDao.findById(userId);
            LOGGER.debug("DAO " + (user.isPresent() ? "found" : "not found") + " user");
        } catch (DaoException | SQLException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return user;
    }

    public void deleteUserById(int userId) throws ServiceException {
        LOGGER.debug("Service delete by user id= " + userId);
        DaoFactory daoFactory = DaoFactory.getInstance();

        UserDao userDao = daoFactory.getUserDao();
        try {
            userDao.deleteById(userId);
            LOGGER.debug("User with id " + userId + " was deleted");
        } catch (DaoException | SQLException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }


    public void deleteUser(User user) throws ServiceException {
        LOGGER.debug("Service delete user= " + user.getLogin());
        DaoFactory daoFactory = DaoFactory.getInstance();

        UserDao userDao = daoFactory.getUserDao();
        try {
            userDao.delete(user);
            LOGGER.debug("User was deleted");
        } catch (DaoException | SQLException e) {
            throw new ServiceException(e.getMessage(), e);
        }

    }


    public User createUser(String login, String firstName, String lastName, String email, String phoneNumber, String password) throws ServiceException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        UserValidation.registrationValidator(login, password, email);
        User user;
        UserDao userDao = daoFactory.getUserDao();
        try {
            userDao.startTransaction();
//            user = new User();
//            user.setLogin(login);
//            user.setFirstName(firstName);
//            user.setLastName(lastName);
//            user.setEmail(email);
//            user.setPhoneNumber(phoneNumber);


            user = userBuilder.withLogin(login)
                    .withFirstName(firstName)
                    .withLastName(lastName)
                    .hasEmail(email)
                    .hasPhoneNumber(phoneNumber)
                    .build();

            userDao.create(user);
            user = userDao.findUserByLoginAndEmail(login, email).orElse(null);
            if (user != null) {
                String hash = PasswordHashKeeper.getInstance().generateHash(login, password);
                userDao.updatePassword(user.getId(), hash);
            }
            userDao.finishTransaction();
        } catch (ConnectionPoolException | DaoException | SQLException e) {
            throw new ServiceException(e);
        }

        LOGGER.debug(user.toString() + " had " + (null == user ? "not" : "") + "been created.");
        return user;
    }


    public User updateUserInfo(int userId, Role role, String firstName, String lastName, String email, String phoneNumber) throws ServiceException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        User user = null;
        UserDao userDao = daoFactory.getUserDao();
        try {
            userDao.startTransaction();
            Optional<User> optionalUser = findUserById(userId);
            if (optionalUser.isPresent()) {
                user = optionalUser.get();
                user.setRole(role);
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setEmail(email);
                user.setPhoneNumber(phoneNumber);
                userDao.updateUserInfo(userId, role, firstName, lastName, email, phoneNumber);
                optionalUser = findUserById(userId);
                if (optionalUser.isPresent()) {
                    user = optionalUser.get();
                    LOGGER.debug("user: " + user.getLogin() + " had been updated.");
                } else {
                    userDao.rollbackTransaction();
                    LOGGER.debug("user: " + user.getLogin() + " had not been updated.");
                }
            } else {
                userDao.rollbackTransaction();
            }
            userDao.finishTransaction();

        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return user;
    }

    public User updateUserPassword(int userId, String password) throws ServiceException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        User user = null;
        UserDao userDao = daoFactory.getUserDao();
        try {
            userDao.startTransaction();
            Optional<User> optionalUser = findUserById(userId);
            if (optionalUser.isPresent()) {
                String hash = PasswordHashKeeper.getInstance().generateHash(optionalUser.get().getLogin(), password);
                userDao.updatePassword(userId, hash);
                user = optionalUser.get();
                LOGGER.debug("user: " + user.getLogin() + " had updated password.");
            } else {
                userDao.rollbackTransaction();
                LOGGER.debug("user: " + user.getLogin() + " had not updated password.");
            }
            userDao.finishTransaction();

        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return user;
    }

    public Optional<List<User>> takeUserSet(int startIndex, int userPerPage) throws ServiceException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        LOGGER.debug("Service looking for all users");
        UserDao userDao = daoFactory.getUserDao();
        Optional<List<User>> userSet = Optional.empty();
        try {
            userSet = Optional.of(userDao.findAllWithLimit(startIndex, userPerPage));
            LOGGER.debug("User set was " + (userSet.isPresent() ? "" : "not") + " found user");
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return userSet;
    }


    public Optional<List<User>> takeUserSetByRole(Role role) throws ServiceException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        LOGGER.debug("Service looking for all users with this" + role);
        UserDao userDao = daoFactory.getUserDao();
        Optional<List<User>> userSet = Optional.empty();
        try {
            userSet = Optional.of(userDao.findUsersByRole(role.name()));
            LOGGER.debug("User set was " + (userSet.isPresent() ? "" : "not") + " found user");
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return userSet;
    }


    public void blockUser(int userId) throws ServiceException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        UserDao userDao = daoFactory.getUserDao();
        try {
            userDao.startTransaction();
            Optional<User> optionalUser = findUserById(userId);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                user.setIsBlocked(0);
                userDao.update(user);
            }
            userDao.finishTransaction();
        } catch (DaoException | SQLException e) {
            throw new ServiceException(e);
        }
        LOGGER.debug("user with id: " + userId + " had " + " been blocked.");
    }


    public void unblockUser(int userId) throws ServiceException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        UserDao userDao = daoFactory.getUserDao();
        try {
            userDao.startTransaction();
            Optional<User> optionalUser = findUserById(userId);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                user.setIsBlocked(1);
                userDao.update(user);

            }
            userDao.finishTransaction();
        } catch (DaoException | SQLException e) {
            throw new ServiceException(e);
        }
        LOGGER.debug("user with id: " + userId + " had " + " been unblocked.");
    }


    public User addFunds(int userId, int amount) throws ServiceException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        UserDao userDao = daoFactory.getUserDao();
        User user = null;
        try {
            userDao.startTransaction();
            Optional<User> optionalUser = findUserById(userId);
            if (optionalUser.isPresent()) {
                User newUser = optionalUser.get();
                newUser.setBalance(newUser.getBalance() + amount);
                userDao.update(newUser);
                user = newUser;
                LOGGER.debug("user with id:" + userId + " had been updated.");
            } else {
                userDao.rollbackTransaction();

            }
            userDao.finishTransaction();
        } catch (DaoException | SQLException e) {
            throw new ServiceException(e);
        }

        return user;
    }


    public User withdrowFunds(int userId, int amount) throws ServiceException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        UserDao userDao = daoFactory.getUserDao();
        User user = null;
        try {
            userDao.startTransaction();
            Optional<User> optionalUser = findUserById(userId);
            if (optionalUser.isPresent() && optionalUser.get().getIsBlocked() != 0) {
                User newUser = optionalUser.get();
                if ((newUser.getBalance() - amount >= 0) && newUser.getIsBlocked() != 0) {
                    newUser.setBalance(newUser.getBalance() - amount);
                    userDao.update(newUser);
                    user = newUser;
                    LOGGER.debug("user with id:" + userId + " had been updated.");

                } else {
                    userDao.rollbackTransaction();
                }
            }
            userDao.finishTransaction();
        } catch (DaoException | SQLException e) {
            throw new ServiceException(e);
        }

        return user;
    }

}
