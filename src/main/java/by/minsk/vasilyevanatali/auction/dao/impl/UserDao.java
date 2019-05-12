package by.minsk.vasilyevanatali.auction.dao.impl;


import by.minsk.vasilyevanatali.auction.connection.ProxyConnection;
import by.minsk.vasilyevanatali.auction.connection.exception.ConnectionPoolException;
import by.minsk.vasilyevanatali.auction.dao.AbstractUserDao;
import by.minsk.vasilyevanatali.auction.dao.exception.DaoException;
import by.minsk.vasilyevanatali.auction.entity.Role;
import by.minsk.vasilyevanatali.auction.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDao extends AbstractUserDao {
    private static final Logger LOGGER = LogManager.getLogger(UserDao.class);

    @Override
    protected boolean prepareStatementForCreate(PreparedStatement statement, User user) throws SQLException {

        statement.setString(1, user.getLogin());
        statement.setString(2, user.getFirstName());
        statement.setString(3, user.getLastName());
        statement.setString(4, user.getEmail());
        statement.setString(5, user.getPhoneNumber());

        int rowChangeNumber = statement.executeUpdate();
        return rowChangeNumber == 1;
    }

    @Override
    protected boolean prepareStatementForUpdate(PreparedStatement statement, User user) throws SQLException {
        statement.setInt(1, user.getRole().ordinal()+1);
        statement.setString(2, user.getLogin());
        statement.setString(3, user.getFirstName());
        statement.setString(4, user.getLastName());
        statement.setString(5, user.getEmail());
        statement.setString(6, user.getPhoneNumber());
        statement.setInt(7, user.getBalance());
        statement.setInt(8, user.getFrozenMoney());
        statement.setInt(9, user.getIsBlocked());
        statement.setInt(10, user.getId());
        int rowChangeNumber = statement.executeUpdate();
        LOGGER.debug("User by id:" + user.getId() + (rowChangeNumber == 1 ? " " : " not") + " updated");
        return rowChangeNumber == 1;
    }

    @Override
    protected User prepareStatementForGetById(PreparedStatement statement) throws SQLException, DaoException {
        ResultSet resultSet = statement.executeQuery();
        User user = new User();
        while (resultSet.next()) {
            setUserParameters(user, resultSet);
        }
        return user;
    }

    @Override
    protected List<User> prepareStatementForGetAll(PreparedStatement statement) throws SQLException, DaoException {
        List<User> users = new ArrayList<>();
        ResultSet resultSet = statement.executeQuery();
        users = parseResultSet(resultSet, users);
        return users;
    }

    @Override
    public boolean updatePassword(int id, String password) throws DaoException {
        LOGGER.debug("Update password for user with id " + id);
        int rowChangeNumber = 0;

        try (ProxyConnection proxyConnection = connectionPool.getConnection();
             PreparedStatement preparedStatement = proxyConnection.prepareStatement(getUpdatePasswordQuery())) {
            preparedStatement.setString(1, password);
            preparedStatement.setInt(2, id);
            rowChangeNumber = preparedStatement.executeUpdate();
            if (rowChangeNumber == 1) {
                LOGGER.debug("Password changed for user with id: " + id);
            } else {
                LOGGER.error("Unable to update password for user with id:" + id);
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        }
        return rowChangeNumber == 1;
    }

    @Override
    public boolean updateUserInfo(int userId, Role role, String firstName, String lastName, String email, String phoneNumber) throws DaoException {
        LOGGER.debug("Update info for user with id " + userId);
        int rowChangeNumber = 0;

        try (ProxyConnection proxyConnection = connectionPool.getConnection();
             PreparedStatement preparedStatement = proxyConnection.prepareStatement(getUpdateUserInfoQuery())) {
            preparedStatement.setInt(1, role.ordinal()+1);
            preparedStatement.setString(2, firstName);
            preparedStatement.setString(3, lastName);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, phoneNumber);
            preparedStatement.setInt(6, userId);
            rowChangeNumber = preparedStatement.executeUpdate();
            if (rowChangeNumber == 1) {
                LOGGER.debug("Password changed for user with id: " + userId);
            } else {
                LOGGER.error("Unable to update password for user with id:" + userId);
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        }
        return rowChangeNumber == 1;
    }

    @Override
    public Optional<User> findUserByLoginAndPassword(String login, String password) throws DaoException, SQLException, ConnectionPoolException {
        LOGGER.debug("User DAO looking for user:" + login);
        Optional<User> optionalUser = Optional.empty();
        try (ProxyConnection proxyConnection = connectionPool.getConnection();
             PreparedStatement preparedStatement = proxyConnection.prepareStatement(getSelectQueryByLoginAndPassword())) {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            LOGGER.trace("Request was sent.");

            optionalUser = Optional.of(parseResultForOneUser(resultSet));
            return optionalUser;
        }
    }

    @Override
    public Optional<User> findUserByLogin(String login) throws DaoException, SQLException, ConnectionPoolException {
        LOGGER.debug("User DAO looking for user:" + login);
        Optional<User> optionalUser = Optional.empty();
        try (ProxyConnection proxyConnection = connectionPool.getConnection();
             PreparedStatement preparedStatement = proxyConnection.prepareStatement(getSelectQueryByLogin())) {
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            LOGGER.trace("Request was sent.");
            optionalUser = Optional.of(parseResultForOneUser(resultSet));

            return optionalUser;
        }
    }

    public boolean findUserByLoginReturnUser(String login) throws DaoException, SQLException, ConnectionPoolException {
        LOGGER.debug("User DAO looking for user:" + login);
       // User user;
        try (ProxyConnection proxyConnection = connectionPool.getConnection();
            PreparedStatement preparedStatement = proxyConnection.prepareStatement(getSelectQueryByLogin())) {
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            LOGGER.trace("Request was sent.");
          // user = parseResultForOneUser(resultSet);
boolean result=resultSet.next();
return result;
           // return user;
        }
    }


    @Override
    public List<User> findAllWithLimit(int offset, int numberOfRecords) throws DaoException {
        LOGGER.debug("Looking for all users with limit on page");
        List<User> users = new ArrayList<>();
        try (ProxyConnection proxyConnection = connectionPool.getConnection();
             PreparedStatement preparedStatement = proxyConnection.prepareStatement(getSelectQueryWithLimit())) {
            preparedStatement.setInt(1, offset);
            preparedStatement.setInt(2, numberOfRecords);
            ResultSet resultSet = preparedStatement.executeQuery();
            users = parseResultSet(resultSet, users);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ConnectionPoolException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public List<User> findUsersByRole(String role) throws DaoException {
        LOGGER.debug("DAO - find all users by role:" + role);
        List<User> users = new ArrayList<>();
        try (ProxyConnection proxyConnection = connectionPool.getConnection();
             PreparedStatement preparedStatement = proxyConnection.prepareStatement(getSelectQueryByRole())) {
            preparedStatement.setString(1, role);
            ResultSet resultSet = preparedStatement.executeQuery();
            users = parseResultSet(resultSet, users);

        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        }
        return users;
    }

    @Override
    public boolean checkLoginAndPassword(String login, String password) throws DaoException {
        try (ProxyConnection proxyConnection = connectionPool.getConnection();
             PreparedStatement prepareStatement = proxyConnection.prepareStatement(getSelectUserIDQueryByLoginPass())) {
            prepareStatement.setString(1, login);
            prepareStatement.setString(2, password);
            ResultSet resultSet = prepareStatement.executeQuery();
            return resultSet.next();
        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public int getNumberOfUsersInStorage() throws DaoException {
        LOGGER.debug("Request for users count");
        Optional<Integer> optionalCount = Optional.empty();
        try (ProxyConnection proxyConnection = connectionPool.getConnection();
             PreparedStatement preparedStatement = proxyConnection.prepareStatement(getSelectCountUsersQuery())) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Integer count = resultSet.getInt(1);
                optionalCount = Optional.of(count);
                LOGGER.debug("Users count: " + count);
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        }
        return optionalCount.orElseThrow(() -> new DaoException("Null users count"));
    }

    @Override
    public Optional<User> findUserByLoginAndEmail(String login, String email) throws DaoException, SQLException, ConnectionPoolException {
        LOGGER.debug("User DAO looking for user:" + login + "with email " + email);
        Optional<User> optionalUser = Optional.empty();
        try (ProxyConnection proxyConnection = connectionPool.getConnection();
             PreparedStatement preparedStatement = proxyConnection.prepareStatement(getSelectQueryByLoginAndEmail())) {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            LOGGER.trace("Request for looking for was sent.");

            optionalUser = Optional.of(parseResultForOneUser(resultSet));
            return optionalUser;
        }
    }



    @Override
    public String getUpdatePasswordQuery() {
        return "UPDATE auctionDB.user SET hash = ? WHERE user_id=?";
    }

    @Override
    public String getUpdateUserInfoQuery() {
        return "UPDATE auctionDB.user SET role_id=?, first_name=?, last_name=?, email=?,  phone=? WHERE user_id=?";
    }

    @Override
    public String getSelectQueryByLoginAndPassword() {
        return "SELECT user_id, role_id, login, first_name, last_name, email, phone, money, frozen_money, is_blocked FROM auctionDB.user WHERE login LIKE ? AND hash LIKE ?;";
    }

    @Override
    public String getSelectQueryWithLimit() {
        return "SELECT user_id, user.role_id, login, first_name, last_name, email, phone, money, frozen_money, is_blocked FROM auctionDB.user JOIN auctionDB.role ON user.role_id = role.role_id limit ?, ?";
    }

    @Override
    public String getSelectQueryByRole() {
        return "SELECT user_id, user.role_id, login, first_name, last_name, email, phone, money, frozen_money, is_blocked FROM auctionDB.user JOIN auctionDB.role ON user.role_id = role.role_id WHERE role_name=?";
    }

    @Override
    public String getSelectQueryByLogin() {
        return "SELECT user_id, user.role_id, login, first_name, last_name, email, phone, money, frozen_money, is_blocked FROM auctionDB.user JOIN auctionDB.role ON user.role_id = role.role_id WHERE login=?";
    }

    @Override
    public String getSelectUserIDQueryByLoginPass() {
        return "SELECT user_id FROM auctionDB.user WHERE login=? and password=?";
    }

    @Override
    public String getSelectCountUsersQuery() {
        return "SELECT COUNT(user_id) FROM auctionDB.user";
    }

    @Override
    public String getSelectQueryByLoginAndEmail() {
        return "SELECT user_id, role_id, login, first_name, last_name, email, phone, money, frozen_money, is_blocked FROM auctionDB.user WHERE login=? and email=?";
    }


    @Override
    public List<User> parseResultSet(ResultSet resultSet, List<User> users) throws DaoException {
        try {
            while (resultSet.next()) {
                User user = new User();
                setUserParameters(user, resultSet);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DaoException();
        }
        return users;
    }

    @Override
    public User parseResultForOneUser(ResultSet resultSet) throws DaoException, SQLException {

        User user = new User();
        while (resultSet.next()) {
            setUserParameters(user, resultSet);
        }
        return user;
    }

    @Override
    public void setUserParameters(User user, ResultSet resultSet) throws DaoException, SQLException {

        user.setId(resultSet.getInt("user_id"));
        user.setRole(Role.getById(resultSet.getInt("role_id")));
        user.setLogin(resultSet.getString("login"));
        user.setFirstName(resultSet.getString("first_name"));
        user.setLastName(resultSet.getString("last_name"));
        user.setEmail(resultSet.getString("email"));
        user.setPhoneNumber(resultSet.getString("phone"));
        user.setBalance(resultSet.getInt("money"));
        user.setFrozenMoney(resultSet.getInt("frozen_money"));
        user.setIsBlocked(resultSet.getInt("is_blocked"));

    }
}
