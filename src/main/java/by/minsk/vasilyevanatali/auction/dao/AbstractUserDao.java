package by.minsk.vasilyevanatali.auction.dao;



import by.minsk.vasilyevanatali.auction.connection.exception.ConnectionPoolException;
import by.minsk.vasilyevanatali.auction.dao.exception.DaoException;
import by.minsk.vasilyevanatali.auction.entity.Role;
import by.minsk.vasilyevanatali.auction.entity.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public abstract class AbstractUserDao extends AbstractDao<User> {
    @Override
    protected abstract boolean prepareStatementForCreate(PreparedStatement statement, User object) throws SQLException;

    @Override
    protected abstract boolean prepareStatementForUpdate(PreparedStatement statement, User object) throws SQLException;

    @Override
    protected abstract User prepareStatementForGetById(PreparedStatement statement) throws SQLException, DaoException;

    @Override
    protected abstract List<User> prepareStatementForGetAll(PreparedStatement statement) throws SQLException, DaoException;

    @Override
    public String getSelectQueryById() {
        return "SELECT user_id, role_id, login, first_name, last_name, email, phone, money, frozen_money, is_blocked FROM auctionDB.user WHERE user_id = ?;";
    }

    @Override
    public String getSelectAllQuery() {
        return "SELECT user_id, role_id, login, first_name, last_name, email, phone, money, frozen_money, is_blocked FROM auctionDB.user JOIN auctionDB.role ON user.role_id = role.role_id";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO auctionDB.user (role_id, login, hash, first_name, last_name, email, phone, money, frozen_money, is_blocked) VALUES (2, ?, 0, ?, ?, ?, ?, 0, 0, 0);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE auctionDB.user SET role_id=?, login =?, first_name=?, last_name=?, email=?, phone=?, money=?, frozen_money=?, is_blocked=? WHERE user_id=?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM auctionDB.user where id_user = ?";
    }

    @Override
    public String getDeleteByIdQuery() {
        return "DELETE FROM auctionDB.user where user_id = ?";
    }

    public abstract String getUpdatePasswordQuery();

    public abstract String getUpdateUserInfoQuery();

    public abstract String getSelectQueryByLoginAndPassword();

    public abstract String getSelectQueryByLoginAndEmail();

    public abstract String getSelectQueryWithLimit();

    public abstract String getSelectQueryByRole();

    public abstract String getSelectQueryByLogin();

    public abstract String getSelectUserIDQueryByLoginPass();

    public abstract String getSelectCountUsersQuery();

    public abstract boolean updatePassword(int id, String password) throws DaoException;

    public abstract boolean updateUserInfo (int userId, Role role, String firstName, String lastName, String email, String phoneNumber) throws DaoException;

    public abstract Optional<User> findUserByLoginAndPassword(String login, String password) throws DaoException, ConnectionPoolException, SQLException;

    public abstract Optional<User> findUserByLoginAndEmail (String login, String email) throws DaoException, ConnectionPoolException, SQLException;

    public abstract List<User> findAllWithLimit(int offset, int numberOfRecords) throws DaoException;

      public abstract List<User> findUsersByRole(String role) throws DaoException;

    public abstract Optional<User> findUserByLogin(String login) throws DaoException, SQLException, ConnectionPoolException;

    public abstract boolean checkLoginAndPassword(String login, String password) throws DaoException;

    public abstract int getNumberOfUsersInStorage() throws DaoException;

    public abstract List<User> parseResultSet(ResultSet resultSet, List<User> users) throws DaoException;

    public abstract User parseResultForOneUser(ResultSet resultSet) throws DaoException, SQLException;

    public abstract void setUserParameters(User user, ResultSet resultSet) throws DaoException, SQLException;
}
