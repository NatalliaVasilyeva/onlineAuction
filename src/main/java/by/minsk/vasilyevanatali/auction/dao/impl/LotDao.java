package by.minsk.vasilyevanatali.auction.dao.impl;


import by.minsk.vasilyevanatali.auction.connection.ProxyConnection;
import by.minsk.vasilyevanatali.auction.connection.exception.ConnectionPoolException;
import by.minsk.vasilyevanatali.auction.dao.AbstractLotDao;
import by.minsk.vasilyevanatali.auction.dao.exception.DaoException;
import by.minsk.vasilyevanatali.auction.entity.Category;
import by.minsk.vasilyevanatali.auction.entity.Lot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class LotDao extends AbstractLotDao {
    private static final Logger LOGGER = LogManager.getLogger(LotDao.class);


    @Override
    protected boolean prepareStatementForCreate(PreparedStatement statement, Lot lot) throws SQLException {
        commonData(statement, lot);
        int rowChangeNumber = statement.executeUpdate();
        return rowChangeNumber == 1;
    }

    @Override
    protected boolean prepareStatementForUpdate(PreparedStatement statement, Lot lot) throws SQLException {
        commonData(statement, lot);
        statement.setBoolean(9, lot.isPaid());
        statement.setInt(10, lot.getIsBlocked());
        int rowChangeNumber = statement.executeUpdate();
        LOGGER.debug("Lot by id:" + lot.getId() + (rowChangeNumber == 1 ? " " : " not") + " updated");
        return rowChangeNumber == 1;
    }

    @Override
    protected Lot prepareStatementForGetById(PreparedStatement statement) throws SQLException, DaoException {
        ResultSet resultSet = statement.executeQuery();
        Lot lot = new Lot();
        while (resultSet.next()) {
            setLotParameters(lot, resultSet);
        }
        return lot;
    }

    @Override
    protected List<Lot> prepareStatementForGetAll(PreparedStatement statement) throws SQLException, DaoException {
        List<Lot> lots = new ArrayList<>();
        ResultSet resultSet = statement.executeQuery();
        lots = parseResultSet(resultSet, lots);
        return lots;
    }

    @Override
    public boolean unApproveLot(int lotId) throws DaoException {
        LOGGER.debug("Unapproved lot:" + lotId);
        boolean isLotUnapprove;
        int rowChangeNumber;
        try (ProxyConnection proxyConnection = connectionPool.getConnection();
             PreparedStatement preparedStatement = proxyConnection.prepareStatement(getUnapproveLotQuery())) {
            preparedStatement.setInt(1, lotId); // `id`
            rowChangeNumber = preparedStatement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        }
        LOGGER.debug("Lot " + lotId + "was unapproved");
        isLotUnapprove = rowChangeNumber == 1;
        return isLotUnapprove;

    }

    @Override
    public boolean approveLot(int lotId) throws DaoException {
        LOGGER.debug("Unapproved lot:" + lotId);
        boolean isLotApproved;
        int rowChangeNumber;
        try (ProxyConnection proxyConnection = connectionPool.getConnection();
             PreparedStatement preparedStatement = proxyConnection.prepareStatement(getApproveLotQuery())) {
            preparedStatement.setInt(1, lotId); // `id`
            rowChangeNumber = preparedStatement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        }
        LOGGER.debug("Lot " + lotId + "was approved");
        isLotApproved = rowChangeNumber == 1;
        return isLotApproved;
    }

    public boolean editLot(Lot lot) throws DaoException {
        LOGGER.debug("Edit lot by user:" + lot.getId());
        boolean isLotEdit;
        int rowChangeNumbers;
        try (ProxyConnection proxyConnection = connectionPool.getConnection();
             PreparedStatement preparedStatement = proxyConnection.prepareStatement(getEditLotQuery())) {
            preparedStatement.setString(1, lot.getName());
            preparedStatement.setString(2, lot.getDescription());
            preparedStatement.setInt(1, lot.getCategory().getId());
            preparedStatement.setInt(1, lot.getId()); // `id`
            rowChangeNumbers = preparedStatement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        }
        LOGGER.debug("Lot " + lot.getId() + "was edit by user");
        isLotEdit = rowChangeNumbers == 1;
        return isLotEdit;
    }

    @Override
    public int proposeLot(boolean isLotCreated) throws DaoException {
        int createdLotId = 0;
        if (isLotCreated) {
            try (ProxyConnection proxyConnection = connectionPool.getConnection();
                 PreparedStatement preparedStatement = proxyConnection.prepareStatement(getSelectIdLastProposedLot())) {
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    createdLotId = resultSet.getInt("id");
                }
            } catch (SQLException | ConnectionPoolException e) {
                throw new DaoException(e);
            }
        }
        LOGGER.debug("Created lot id is: " + createdLotId);
        return createdLotId;
    }

    @Override
    public List<Lot> takeProposedLotSet() throws DaoException {
        LOGGER.debug("Send request for proposed lot set.");
        List<Lot> setLots = new ArrayList<>();
        try (ProxyConnection proxyConnection = connectionPool.getConnection();
             PreparedStatement preparedStatement = proxyConnection.prepareStatement(getProposedLotSetQuery())) {
            ResultSet resultSet = preparedStatement.executeQuery();
            setLots = parseResultSetForLotsSet(resultSet, setLots);
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        }
        return setLots;
    }

    @Override
    public String takeCategoryNameById(int categoryId) throws DaoException {
        String name = null;
        try (ProxyConnection proxyConnection = connectionPool.getConnection();
             PreparedStatement preparedStatement = proxyConnection.prepareStatement(getCategoryNameQuery())) {
            preparedStatement.setInt(1, categoryId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                name = resultSet.getString("category_name");
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        }
        return name;
    }

    @Override
    public List<Lot> takeUnfinishedLotList() throws DaoException {
        List<Lot> lots = new ArrayList<>();
        try (ProxyConnection proxyConnection = connectionPool.getConnection();
             PreparedStatement preparedStatement = proxyConnection.prepareStatement(getUnfinishedLotQuery())) {
            ResultSet resultSet = preparedStatement.executeQuery();
            lots = setLotParametersForTakeLotsList(lots, resultSet);
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        }
        return lots;
    }

    @Override
    public List<Lot> takeLotsListBySellerId(int sellerId) throws DaoException {
        List<Lot> lots = new ArrayList<>();
        try (ProxyConnection proxyConnection = connectionPool.getConnection();
             PreparedStatement preparedStatement = proxyConnection.prepareStatement(getAllLotsBySellerIdQuery())) {
            preparedStatement.setInt(1, sellerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            lots = setLotParametersForTakeLotsList(lots, resultSet);
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        }
        return lots;
    }

    @Override
    public List<Lot> takeLotsListByBidderId(int bidderId) throws DaoException {
        List<Lot> lots = new ArrayList<>();
        try (ProxyConnection proxyConnection = connectionPool.getConnection();
             PreparedStatement preparedStatement = proxyConnection.prepareStatement(getAllLotsByBidderIdQuery())) {
            System.out.println("dao 1");
            preparedStatement.setInt(1, bidderId);
            System.out.println("dao 2");
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("dao 3");
            lots = setLotParametersForTakeLotsList(lots, resultSet);
            System.out.println("dao 4");
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        }
        return lots;
    }

    @Override
    public List<Lot> takeAllLotsLimit(int startIndex, int quantity) throws DaoException, SQLException, ConnectionPoolException {
        List<Lot> setLots = new ArrayList<>();

        try (ProxyConnection proxyConnection = connectionPool.getConnection();
             PreparedStatement preparedStatement = proxyConnection.prepareStatement(getSelectAllQueryLimit())) {
            preparedStatement.setInt(1, startIndex);
            preparedStatement.setInt(2, quantity);
            ResultSet resultSet = preparedStatement.executeQuery();

            setLots = parseResultSet(resultSet, setLots);
        }
        return setLots;
    }

    @Override
    public List<Lot> takeAllLotsLimitInAuction(int auctionId, int startIndex, int quantity) throws DaoException, SQLException, ConnectionPoolException {
        List<Lot> setLots = new ArrayList<>();

        try (ProxyConnection proxyConnection = connectionPool.getConnection();
             PreparedStatement preparedStatement = proxyConnection.prepareStatement(getSelectAllInAuctionQueryLimit())) {
            preparedStatement.setInt(1, auctionId);
            preparedStatement.setInt(2, startIndex);
            preparedStatement.setInt(3, quantity);
            ResultSet resultSet = preparedStatement.executeQuery();

            setLots = parseResultSet(resultSet, setLots);
        }
        return setLots;
    }


    @Override
    public String getUnapproveLotQuery() {
        return "UPDATE auctionDB.lot SET is_blocked=0  WHERE lot.lot_id= ?";
    }

    @Override
    public String getApproveLotQuery() {
        return "UPDATE auctionDB.lot SET is_blocked=1  WHERE lot.lot_id= ?";
    }


    public String getEditLotQuery() {
        return "UPDATE auctionDB.lot SET lot_name=?, description=?, category_id=? WHERE lot.lot_id= ?";
    }


    @Override
    public String getProposedLotSetQuery() {
        return "SELECT lot_id, lot_name, description, category_id, auction_id, user_id as seller_id, start_price, min_step, is_paid, is_blocked, image_path FROM auctionDB.lot  WHERE lot.is_blocked = 0;";
    }

    @Override
    public String getCategoryNameQuery() {
        return "SELECT category_name FROM auctionDB.category WHERE category_id =?";
    }

    @Override
    public String getUnfinishedLotQuery() {
        return "SELECT LOT.lot_id, LOT.lot_name, LOT.description, LOT.is_blocked, LOT.is_paid, LOT.image_path, CATEGORY.category_name AS category, AUCTION.start_time, AUCTION.finish_time, LOT.user_id, LOT.auction_id "
                + "FROM auctionDB.lot LOT JOIN auctionDB.category CATEGORY ON LOT.category_id = CATEGORY.category_id"
                + "    JOIN auctionDB.auction AUCTION ON LOT.auction_id = AUCTION.auction_id"
                + "    WHERE LOT.is_blocked=1 AND finish_time > now()";
    }

    @Override
    public String getAllLotsBySellerIdQuery() {
        return "SELECT LOT.lot_id, LOT.lot_name, LOT.description, LOT.is_blocked, LOT.is_paid, LOT.image_path, CATEGORY.category_name AS category, AUCTION.start_time, AUCTION.finish_time, LOT.user_id, LOT.auction_id "
                + "FROM auctionDB.lot LOT JOIN auctionDB.category CATEGORY ON LOT.category_id = CATEGORY.category_id"
                + "    JOIN auctionDB.auction AUCTION ON LOT.auction_id = AUCTION.auction_id"
                + "    WHERE LOT.user_id=?";
    }


    @Override
    public String getAllLotsByBidderIdQuery() {
        return "SELECT LOT.lot_id, LOT.lot_name, LOT.description, LOT.is_blocked, LOT.is_paid, LOT.image_path, CATEGORY.category_name AS category, AUCTION.start_time, AUCTION.finish_time, LOT.user_id, LOT.auction_id,  BID.user_id "
                + "FROM auctionDB.lot LOT JOIN auctionDB.category CATEGORY ON LOT.category_id = CATEGORY.category_id"
                + " JOIN auctionDB.auction AUCTION ON LOT.auction_id = AUCTION.auction_id"
                + " JOIN auctionDB.bid BID on LOT.lot_id = BID.lot_id"
                + "    WHERE BID.user_id=?";
    }


    @Override
    public String getSelectAllQueryLimit() {
        return "SELECT LOT.lot_id AS lot_id, LOT.lot_name AS lot_name, LOT.description AS description, LOT.is_blocked AS lot_is_approved, " +
                "LOT.is_paid AS lot_is_paid, LOT.image_path as image, AUCTION.auction_id AS auction_id, AUCTION.start_time AS auction_start_time, " +
                "AUCTION.finish_time AS auction_finish_time, LOT.start_price AS start_price, LOT.min_step as min_step, LOT.user_id AS seller_id, SELLER.email AS seller_email, SELLER.login " +
                "AS seller_login, SELLER.is_blocked AS seller_is_blocked, " +
                "LOT.category_id AS category_name, CASE WHEN AUCTION.auction_type like 'direct' THEN MAX(BID.bid_amount) ELSE MIN(BID.bid_amount) END AS bid_price, " +
                "BID.bid_id AS bid_id, BID.user_id AS bidder_id FROM auctionDB.lot LOT LEFT JOIN auctionDB.auction AUCTION " +
                "ON LOT.auction_id = AUCTION.auction_id LEFT JOIN auctionDB.user SELLER ON LOT.user_id = SELLER.user_id " +
                "LEFT JOIN auctionDB.bid BID ON LOT.lot_id = BID.lot_id GROUP BY lot_id limit ?, ? ";
    }


    @Override
    public String getSelectAllInAuctionQueryLimit() {
        return "SELECT LOT.lot_id AS lot_id, LOT.lot_name AS lot_name, LOT.description AS description, LOT.is_blocked AS lot_is_approved, " +
                "LOT.is_paid AS lot_is_paid, LOT.image_path as image, AUCTION.auction_id AS auction_id, AUCTION.start_time AS auction_start_time, " +
                "AUCTION.finish_time AS auction_finish_time, LOT.user_id AS seller_id, SELLER.email AS seller_email, SELLER.login AS seller_login, SELLER.is_blocked AS seller_is_blocked," +
                " LOT.category_id AS category_name, LOT.start_price AS start_price, LOT.min_step AS min_step, CASE WHEN Auction.auction_type like 'direct' THEN MAX(BID.bid_amount) ELSE MIN(BID.bid_amount) END AS bid_price, " +
                "BID.bid_id AS bid_id, BID.user_id AS bidder_id FROM auctionDB.lot LOT LEFT JOIN auctionDB.auction AUCTION ON LOT.auction_id = AUCTION.auction_id LEFT JOIN auctionDB.user SELLER ON LOT.user_id = SELLER.user_id " +
                "LEFT JOIN auctionDB.bid BID ON LOT.lot_id = BID.lot_id GROUP BY lot_id HAVING AUCTION.auction_id=? LIMIT ?,? ";
    }

    @Override
    public String getSelectIdLastProposedLot() {
        return "SELECT MAX(lot_id) AS id FROM auctionDB.lot;";
    }

    @Override
    protected void commonData(PreparedStatement statement, Lot lot) throws SQLException {
        statement.setString(1, lot.getName());
        statement.setString(2, lot.getDescription());
        statement.setInt(3, lot.getCategory().ordinal() + 1);
        statement.setInt(4, lot.getAuctionId());
        statement.setInt(5, lot.getSellerId());
        statement.setInt(6, lot.getStartPrice());
        statement.setInt(7, lot.getMinimumStep());
        statement.setString(8, lot.getImagePath());
    }

    @Override
    protected void setLotParameters(Lot lot, ResultSet resultSet) throws SQLException {

        lot.setId(resultSet.getInt("lot_id"));
        lot.setName(resultSet.getString("lot_name"));
        lot.setDescription(resultSet.getString("description"));
        lot.setCategory(Category.getById(resultSet.getInt("category_name")));
        lot.setAuctionId(resultSet.getInt("auction_id"));
        lot.setSellerId(resultSet.getInt("seller_id"));
        lot.setStartPrice(resultSet.getInt("start_price"));
        lot.setMinimumStep(resultSet.getInt("min_step"));
        lot.setPaid(resultSet.getBoolean("lot_is_paid"));
        lot.setIsBlocked(resultSet.getInt("lot_is_approved"));
        lot.setStartTime((LocalDateTime.ofInstant(resultSet.getTimestamp("auction_start_time").toInstant(), ZoneId.systemDefault())));
        lot.setFinishTime((LocalDateTime.ofInstant(resultSet.getTimestamp("auction_finish_time").toInstant(), ZoneId.systemDefault())));
        lot.setSellerEmail(resultSet.getString("seller_email"));
        lot.setSellerLogin(resultSet.getString("seller_login"));
        lot.setSellerIsBlocked(resultSet.getInt("seller_is_blocked"));
        lot.setBidPrice(resultSet.getInt("bid_price"));
        lot.setBidId(resultSet.getInt("bid_id"));
        lot.setBidderId(resultSet.getInt("bidder_id"));
        lot.setImagePath(resultSet.getString("image_path"));

    }

    @Override
    protected List<Lot> setLotParametersForTakeLotsList(List<Lot> lots, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            Lot lot = new Lot();
            lot.setId(resultSet.getInt("lot_id"));
            lot.setName(resultSet.getString("lot_name"));
            lot.setDescription(resultSet.getString("description"));
            lot.setIsBlocked(resultSet.getInt("is_blocked"));
            lot.setPaid(resultSet.getBoolean("is_paid"));
            //  lot.setCategory(Category.valueOf(resultSet.getString("category_name")));
            lot.setCategory(Category.valueOf(resultSet.getString("category")));
//            lot.setStartTime((LocalDateTime.ofInstant(resultSet.getTimestamp("auction_start_time").toInstant(), ZoneId.systemDefault())));
//            lot.setFinishTime((LocalDateTime.ofInstant(resultSet.getTimestamp("auction_finish_time").toInstant(), ZoneId.systemDefault())));
            lot.setStartTime((LocalDateTime.ofInstant(resultSet.getTimestamp("start_time").toInstant(), ZoneId.systemDefault())));
            lot.setFinishTime((LocalDateTime.ofInstant(resultSet.getTimestamp("finish_time").toInstant(), ZoneId.systemDefault())));
//            lot.setSellerId(resultSet.getInt("seller_id"));
            lot.setSellerId(resultSet.getInt("user_id"));
            lot.setAuctionId(resultSet.getInt("auction_id"));
            lot.setImagePath(resultSet.getString("image_path"));
            lots.add(lot);
        }
        return lots;
    }

    private void setLotParametersForProposeLotSet(Lot lot, ResultSet resultSet) throws SQLException {

        lot.setId(resultSet.getInt("lot_id"));
        lot.setName(resultSet.getString("lot_name"));
        lot.setDescription(resultSet.getString("description"));
        lot.setCategory(Category.getById(resultSet.getInt("category_id")));
        lot.setAuctionId(resultSet.getInt("auction_id"));
        lot.setSellerId(resultSet.getInt("seller_id"));
        lot.setStartPrice(resultSet.getInt("start_price"));
        lot.setMinimumStep(resultSet.getInt("min_step"));
        lot.setPaid(resultSet.getBoolean("is_paid"));
        lot.setIsBlocked(resultSet.getInt("is_blocked"));
        lot.setImagePath(resultSet.getString("image_path"));
    }


    @Override
    protected List<Lot> parseResultSet(ResultSet resultSet, List<Lot> lots) throws DaoException {
        try {
            while (resultSet.next()) {
                Lot lot = new Lot();
                setLotParameters(lot, resultSet);
                lots.add(lot);
            }
        } catch (SQLException e) {
            throw new DaoException();
        }
        return lots;
    }

    private List<Lot> parseResultSetForLotsSet(ResultSet resultSet, List<Lot> lots) throws DaoException {
        try {
            while (resultSet.next()) {
                Lot lot = new Lot();
                setLotParametersForProposeLotSet(lot, resultSet);
                lots.add(lot);
            }
        } catch (SQLException e) {
            throw new DaoException();
        }
        return lots;
    }
}
