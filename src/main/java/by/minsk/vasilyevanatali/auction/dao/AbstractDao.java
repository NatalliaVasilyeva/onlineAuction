package by.minsk.vasilyevanatali.auction.dao;

import by.minsk.vasilyevanatali.auction.connection.ConnectionPool;
import by.minsk.vasilyevanatali.auction.connection.ProxyConnection;
import by.minsk.vasilyevanatali.auction.connection.exception.ConnectionPoolException;
import by.minsk.vasilyevanatali.auction.dao.exception.DaoException;
import by.minsk.vasilyevanatali.auction.entity.Bean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDao<T extends Bean> implements BaseDao<T> {
    private static final Logger LOGGER = LogManager.getLogger(AbstractDao.class);

    protected final ConnectionPool connectionPool = ConnectionPool.getInstance();

    protected abstract boolean prepareStatementForCreate(PreparedStatement statement, T object) throws SQLException;

    private void prepareStatementForDelete(PreparedStatement statement, T object) throws SQLException {
        statement.setInt(1, object.getId());
        statement.executeUpdate();
    }

    private boolean prepareStatementForDeleteById(PreparedStatement statement, int id) throws SQLException {
        int rowChangeNumber = 0;
        statement.setInt(1, id);
        statement.executeUpdate();
        return rowChangeNumber == 1;
    }

    protected abstract boolean prepareStatementForUpdate(PreparedStatement statement, T object) throws SQLException;

    protected abstract T prepareStatementForGetById(PreparedStatement statement) throws SQLException, DaoException;

    protected abstract List<T> prepareStatementForGetAll(PreparedStatement statement) throws SQLException, DaoException;

    protected abstract String getSelectQueryById();

    protected abstract String getSelectAllQuery();

    protected abstract String getCreateQuery();

    protected abstract String getUpdateQuery();

    protected abstract String getDeleteQuery();

    protected abstract String getDeleteByIdQuery();

    @Override
    public Optional<T> findById(int id) throws DaoException, SQLException {
        Optional<T> optional = Optional.empty();
        try (ProxyConnection proxyConnection = connectionPool.getConnection();
             PreparedStatement preparedStatement = proxyConnection.prepareStatement(getSelectQueryById())) {
            preparedStatement.setInt(1, id);
            optional = Optional.of(prepareStatementForGetById(preparedStatement));
            return optional;
        } catch (ConnectionPoolException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<T> findAll() throws SQLException, DaoException {
        try (ProxyConnection proxyConnection = connectionPool.getConnection();
             PreparedStatement preparedStatement = proxyConnection.prepareStatement(getSelectAllQuery())) {
            return prepareStatementForGetAll(preparedStatement);
        } catch (ConnectionPoolException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean create(T object) throws SQLException, DaoException {
        boolean isUpdate = false;
        try (ProxyConnection proxyConnection = connectionPool.getConnection();
             PreparedStatement preparedStatement = proxyConnection.prepareStatement(getCreateQuery())) {
            isUpdate = prepareStatementForCreate(preparedStatement, object);
        } catch (ConnectionPoolException e) {
            throw new DaoException(e);
        }
        return isUpdate;
    }


    @Override
    public boolean deleteById(int id) throws SQLException, DaoException {
        boolean isDelete;
        try (ProxyConnection proxyConnection = connectionPool.getConnection();
             PreparedStatement preparedStatement = proxyConnection.prepareStatement(getDeleteByIdQuery())) {
            isDelete = prepareStatementForDeleteById(preparedStatement, id);
        } catch (ConnectionPoolException e) {
            throw new DaoException(e);
        }
        return isDelete;
    }

    @Override
    public void delete(T object) throws SQLException, DaoException {
        try (ProxyConnection proxyConnection = connectionPool.getConnection();
             PreparedStatement preparedStatement = proxyConnection.prepareStatement(getDeleteQuery())) {
            prepareStatementForDelete(preparedStatement, object);
        } catch (ConnectionPoolException e) {
            throw new DaoException(e);
        }
    }

    @Override

    public boolean update(T object) throws SQLException, DaoException {
        boolean isUpdate = false;
        try (ProxyConnection proxyConnection = connectionPool.getConnection();
             PreparedStatement preparedStatement = proxyConnection.prepareStatement(getUpdateQuery())) {
            isUpdate = prepareStatementForUpdate(preparedStatement, object);
        } catch (ConnectionPoolException e) {
            throw new DaoException(e);
        }
        return isUpdate;
    }

    public void startTransaction() throws DaoException {
        try {
            connectionPool.getConnection().setAutoCommit(false);
            LOGGER.debug("start Transaction");
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        }
    }

    public void finishTransaction() throws DaoException {
        try {
            connectionPool.getConnection().setAutoCommit(true);
            LOGGER.debug("finish Transaction");
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        }
    }

    public void rollbackTransaction() throws DaoException {
        try {

            connectionPool.getConnection().rollback();
            LOGGER.debug("rollback Transaction");
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        }
    }

}
