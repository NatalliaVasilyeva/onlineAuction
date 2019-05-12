package by.minsk.vasilyevanatali.auction.dao;



import by.minsk.vasilyevanatali.auction.dao.exception.DaoException;
import by.minsk.vasilyevanatali.auction.entity.Bid;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public abstract class AbstractBidDao extends AbstractDao<Bid> {
    @Override
    protected abstract boolean prepareStatementForCreate(PreparedStatement statement, Bid object) throws SQLException;

    @Override
    protected abstract boolean prepareStatementForUpdate(PreparedStatement statement, Bid object) throws SQLException;

    @Override
    protected abstract Bid prepareStatementForGetById(PreparedStatement statement) throws SQLException, DaoException;

    @Override
    protected abstract List<Bid> prepareStatementForGetAll(PreparedStatement statement) throws SQLException, DaoException;

    @Override
    public String getSelectQueryById() {
        return null;
    }

    @Override
    public String getSelectAllQuery() {
        return null;
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO auctionDB.bid (bid_amount, user_id, lot_id) VALUES (?, ?, ?);";
    }

    @Override
    public String getUpdateQuery() {
        return null;
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM auctionDB.bid WHERE `bid_id`=?";
    }

    @Override
    public String getDeleteByIdQuery() {
        return "DELETE FROM auctionDB.bid WHERE `bid_id`=?";
    }

    public abstract String getSelectAllQueryByLotId();

    public abstract String getSelectAllInfoMaxBidQuery();

    public abstract String getSelectAllInfoMinBidQuery();

    public abstract String getSelectMaxBidQuery();

    public abstract String getSelectMinBidQuery();

    public abstract List<Bid> getAllBidsByLotId(int lotId) throws DaoException;

    public abstract Optional<Bid> findAllInfoAboutMaxBid(int lotId) throws DaoException;

    public abstract Optional<Bid> findAllInfoAboutMinBid(int lotId) throws DaoException;

    public abstract Optional<Integer> findLotMaxBidPrice(int lotId) throws DaoException;

    public abstract Optional<Integer> findLotMinBidPrice(int lotId) throws DaoException;

    protected abstract void setBidParameters(Bid bid, ResultSet resultSet) throws DaoException, SQLException;

    protected abstract List<Bid> parseResultSet(ResultSet resultSet, List<Bid> bids) throws DaoException;

    protected abstract Bid parseResultForOneBid(ResultSet resultSet) throws DaoException, SQLException;

}
