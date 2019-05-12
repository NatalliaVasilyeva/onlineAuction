package by.minsk.vasilyevanatali.auction.dao.impl;


import by.minsk.vasilyevanatali.auction.connection.ProxyConnection;
import by.minsk.vasilyevanatali.auction.connection.exception.ConnectionPoolException;
import by.minsk.vasilyevanatali.auction.dao.AbstractAuctionDao;
import by.minsk.vasilyevanatali.auction.dao.exception.DaoException;
import by.minsk.vasilyevanatali.auction.entity.Auction;
import by.minsk.vasilyevanatali.auction.entity.AuctionType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AuctionDao extends AbstractAuctionDao {
    private static final Logger LOGGER = LogManager.getLogger(AuctionDao.class);


    @Override
    protected boolean prepareStatementForCreate(PreparedStatement statement, Auction auction) throws SQLException {
        int rowChangeNumber = 0;
        statement.setTimestamp(1, Timestamp.valueOf(auction.getStartTime()));
        statement.setTimestamp(2, Timestamp.valueOf(auction.getFinishTime()));
        statement.setString(3, auction.getAuctionType().getName());
        statement.setString(4, auction.getDescription());
        rowChangeNumber = statement.executeUpdate();
        return rowChangeNumber == 1;
    }

    @Override
    protected boolean prepareStatementForUpdate(PreparedStatement statement, Auction auction) throws SQLException {
        return false;
    }

    @Override
    protected Auction prepareStatementForGetById(PreparedStatement statement) throws SQLException, DaoException {
        ResultSet resultSet = statement.executeQuery();
        Auction auction = new Auction();
        while (resultSet.next()) {
            setAuctionParameters(auction, resultSet);
        }
        return auction;

    }

    @Override
    protected List<Auction> prepareStatementForGetAll(PreparedStatement statement) throws SQLException, DaoException {
        List<Auction> auctions = new ArrayList<>();
        ResultSet resultSet = statement.executeQuery();
        auctions = parseResultSet(resultSet, auctions);
        return auctions;

    }

    @Override
    protected int getNumberOfAuctionsInStorage() throws DaoException {
        LOGGER.debug("Request for active auctions count");
        Optional<Integer> optionalCount = Optional.empty();
        try (ProxyConnection proxyConnection = connectionPool.getConnection();
             PreparedStatement preparedStatement = proxyConnection.prepareStatement(getSelectCountAuctionsQuery())) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Integer count = resultSet.getInt(1);
                optionalCount = Optional.of(count);
                LOGGER.debug("Auctions count: " + count);
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        }
        return optionalCount.orElseThrow(() -> new DaoException("Null auction count"));
    }


    public String getSelectCountAuctionsQuery() {
        return "SELECT COUNT(auction_id) FROM auctionDB.auction WHERE finish_time > now();";
    }

    protected void setAuctionParameters(Auction auction, ResultSet resultSet) throws DaoException, SQLException {
        auction.setId(resultSet.getInt("auction_id"));
        auction.setStartTime(LocalDateTime.ofInstant(resultSet.getTimestamp("start_time").toInstant(),
                ZoneId.systemDefault()));
        auction.setFinishTime(LocalDateTime.ofInstant(resultSet.getTimestamp("finish_time").toInstant(),
                ZoneId.systemDefault()));
        auction.setAuctionType(AuctionType.valueOf((resultSet.getString("auction_type")).toUpperCase()));
        auction.setDescription(resultSet.getString("description"));
    }

    protected List<Auction> parseResultSet(ResultSet resultSet, List<Auction> auctions) throws DaoException {
        try {
            while (resultSet.next()) {
                Auction auction = new Auction();
                setAuctionParameters(auction, resultSet);
                auctions.add(auction);
            }
        } catch (SQLException e) {
            throw new DaoException();
        }
        return auctions;
    }
}
