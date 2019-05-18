package by.minsk.vasilyevanatali.auction.dao;


import by.minsk.vasilyevanatali.auction.dao.exception.DaoException;
import by.minsk.vasilyevanatali.auction.entity.Bean;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface BaseDao<T extends Bean> {
    List<T> findAll() throws SQLException, DaoException;

    Optional<T> findById(int id) throws DaoException, SQLException, InterruptedException;

    boolean deleteById(int id) throws SQLException, DaoException;

    void delete(T object) throws SQLException, DaoException;

    boolean create(T object) throws SQLException, DaoException;

    boolean update(T object) throws SQLException, DaoException;
}
