package by.minsk.vasilyevanatali.auction.dao;



import by.minsk.vasilyevanatali.auction.dao.exception.DaoException;
import by.minsk.vasilyevanatali.auction.entity.Auction;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class AbstractAuctionDao extends AbstractDao<Auction> {

    @Override
    protected abstract boolean prepareStatementForCreate(PreparedStatement statement, Auction auction) throws SQLException;

    @Override
    protected abstract boolean prepareStatementForUpdate(PreparedStatement statement, Auction object) throws SQLException;

    @Override
    protected abstract Auction prepareStatementForGetById(PreparedStatement statement) throws SQLException, DaoException;


    @Override
    protected abstract List<Auction> prepareStatementForGetAll(PreparedStatement statement) throws SQLException, DaoException;


    protected abstract int getNumberOfAuctionsInStorage() throws DaoException;

    @Override
    public String getSelectQueryById() {
        return "SELECT auction_id, start_time, finish_time, auction_type, description, owner_id FROM auctionDB.auction join auctionDB.user on auction.owner_id=user.user_id WHERE auction.auction_id = ?;";
    }

    @Override
    public String getSelectAllQuery() {
        return "SELECT auction_id, start_time, finish_time, auction_type, description FROM auctionDB.auction WHERE finish_time > now();";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO auctionDB.auction (start_time, finish_time, auction_type, description, owner_id) VALUES (?, ?, ?, ?, ?);";
    }

    @Override
    public String getUpdateQuery() {
        return null;
    }

    @Override
    public String getDeleteQuery() {
        return null;
    }

    @Override
    public String getDeleteByIdQuery() {
        return null;
    }

    public abstract String getSelectCountAuctionsQuery();

    public abstract String getSelectUserAuctionsAllQuery();

    public abstract String getSelectMaxUserAuctionIdQuery();

    protected abstract List<Auction> parseResultSet(ResultSet resultSet, List<Auction> auctions) throws DaoException;

    protected abstract void setAuctionParameters(Auction auction, ResultSet resultSet) throws DaoException, SQLException;
}
