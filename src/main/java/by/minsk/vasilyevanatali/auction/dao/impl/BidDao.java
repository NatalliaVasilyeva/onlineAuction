package by.minsk.vasilyevanatali.auction.dao.impl;


import by.minsk.vasilyevanatali.auction.connection.ProxyConnection;
import by.minsk.vasilyevanatali.auction.connection.exception.ConnectionPoolException;
import by.minsk.vasilyevanatali.auction.dao.AbstractBidDao;
import by.minsk.vasilyevanatali.auction.dao.exception.DaoException;
import by.minsk.vasilyevanatali.auction.entity.Bid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BidDao extends AbstractBidDao {
    private static final Logger LOGGER = LogManager.getLogger(BidDao.class);

    @Override
    protected boolean prepareStatementForCreate(PreparedStatement statement, Bid bid) throws SQLException {
        int rowChangeNumber = 0;
        statement.setInt(1, bid.getBidAmount());
        statement.setInt(2, bid.getUserId());
        statement.setInt(3, bid.getLotId());
        rowChangeNumber = statement.executeUpdate();
        return rowChangeNumber == 1;
    }

    @Override
    protected boolean prepareStatementForUpdate(PreparedStatement statement, Bid bid) {
        return false;

    }

    @Override
    protected Bid prepareStatementForGetById(PreparedStatement statement) {
        return null;
    }

    @Override
    protected List<Bid> prepareStatementForGetAll(PreparedStatement statement) {
        return null;
    }

    @Override
    public List<Bid> getAllBidsByLotId(int lotId) throws DaoException {
        List<Bid> bids = new ArrayList<>();
        try (ProxyConnection proxyConnection = connectionPool.getConnection();
             PreparedStatement preparedStatement = proxyConnection.prepareStatement(getSelectAllQueryByLotId())) {
            preparedStatement.setInt(1, lotId);
            ResultSet resultSet = preparedStatement.executeQuery();
            bids = parseResultSet(resultSet, bids);

        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        }
        return bids;
    }

    @Override
    public Optional<Bid> findAllInfoAboutMaxBid(int lotId) throws DaoException {
        Optional<Bid> maxBid = Optional.empty();
        try (ProxyConnection proxyConnection = connectionPool.getConnection();
             PreparedStatement preparedStatement = proxyConnection.prepareStatement(getSelectAllInfoMaxBidQuery())) {
            preparedStatement.setInt(1, lotId);
            ResultSet resultSet = preparedStatement.executeQuery();
            LOGGER.trace("Request was sent.");
            maxBid = Optional.of(parseResultForOneBid(resultSet));
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        }
        return maxBid;
    }

    @Override
    public Optional<Bid> findAllInfoAboutMinBid(int lotId) throws DaoException {
        Optional<Bid> minBid = Optional.empty();
        try (ProxyConnection proxyConnection = connectionPool.getConnection();
             PreparedStatement preparedStatement = proxyConnection.prepareStatement(getSelectAllInfoMinBidQuery())) {
            preparedStatement.setInt(1, lotId);
            ResultSet resultSet = preparedStatement.executeQuery();
            LOGGER.trace("Request was sent.");
            minBid = Optional.of(parseResultForOneBid(resultSet));
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        }
        return minBid;
    }

    @Override
    public Optional<Integer> findLotMaxBidPrice(int lotId) throws DaoException {
        Optional<Integer> bid_amount = Optional.empty();
        try (ProxyConnection proxyConnection = connectionPool.getConnection();
             PreparedStatement preparedStatement = proxyConnection.prepareStatement(getSelectMaxBidQuery())) {
            preparedStatement.setInt(1, lotId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                bid_amount = Optional.of(resultSet.getInt("bid_amount"));
                LOGGER.debug("Max bid for lot with id: " + lotId + " found:" + bid_amount.get());
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        }
        return bid_amount;
    }

    @Override
    public Optional<Integer> findLotMinBidPrice(int lotId) throws DaoException {
        Optional<Integer> bid_amount = Optional.empty();
        try (ProxyConnection proxyConnection = connectionPool.getConnection();
             PreparedStatement preparedStatement = proxyConnection.prepareStatement(getSelectMinBidQuery())) {
            preparedStatement.setInt(1, lotId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                bid_amount = Optional.of(resultSet.getInt("bid_amount"));
                LOGGER.debug("Min bid for lot with id: " + lotId + " found:" + bid_amount.get());
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        }
        return bid_amount;
    }

    @Override
    public String getSelectAllQueryByLotId() {
        return "SELECT bid_id, bid_amount, user_id, lot_id, login, lot_name FROM auctionDB.bid left join user on bid.user_id=user.user_id left join lot on bid.lot_id=lot.lot_id WHERE bid.lot_id=?`";
    }

    @Override
    public String getSelectAllInfoMaxBidQuery() {
        return "SELECT bid_id, bid_amount, user_id, lot_id, login, lot_name FROM auctionDB.bid left join user on bid.user_id=user.user_id left join lot on bid.lot_id=lot.lot_id WHERE bid_amount = (SELECT MAX(bid_amount) FROM auction.bid WHERE bid.lot_id = ?);";
    }

    @Override
    public String getSelectAllInfoMinBidQuery() {
        return "SELECT bid_id, bid_amount, user_id, lot_id, login, lot_name FROM auctionDB.bid left join user on bid.user_id=user.user_id left join lot on bid.lot_id=lot.lot_id WHERE bid_amount = (SELECT MIN(bid_amount) FROM auction.bid WHERE bid.lot_id = ?);";
    }

    @Override
    public String getSelectMaxBidQuery() {
        return "SELECT MAX(bid_amount) FROM auctionDB.bid WHERE bid.lot_id = ?;";
    }

    @Override
    public String getSelectMinBidQuery() {
        return "SELECT MIN(bid_amount) FROM auctionDB.bid WHERE bid.lot_id = ?;";
    }

    @Override
    protected void setBidParameters(Bid bid, ResultSet resultSet) throws SQLException {
        bid.setId(resultSet.getInt("bid_id"));
        bid.setBidAmount(resultSet.getInt("bid_amount"));
        bid.setUserId(resultSet.getInt("user_id"));
        bid.setLotId(resultSet.getInt("lot_id"));
    }

    @Override
    protected List<Bid> parseResultSet(ResultSet resultSet, List<Bid> bids) throws DaoException {
        try {
            while (resultSet.next()) {
                Bid bid = new Bid();
                setBidParameters(bid, resultSet);
                bids.add(bid);
            }
        } catch (SQLException e) {
            throw new DaoException();
        }
        return bids;
    }

    @Override
    protected Bid parseResultForOneBid(ResultSet resultSet) throws SQLException {

        Bid bid = new Bid();
        while (resultSet.next()) {
            setBidParameters(bid, resultSet);
        }
        return bid;
    }
}
