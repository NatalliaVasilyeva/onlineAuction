package by.minsk.vasilyevanatali.auction.dao;



import by.minsk.vasilyevanatali.auction.connection.exception.ConnectionPoolException;
import by.minsk.vasilyevanatali.auction.dao.exception.DaoException;
import by.minsk.vasilyevanatali.auction.entity.Lot;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class AbstractLotDao extends AbstractDao<Lot> {
    @Override
    protected abstract boolean prepareStatementForCreate(PreparedStatement statement, Lot object) throws SQLException;

    @Override
    protected abstract boolean prepareStatementForUpdate(PreparedStatement statement, Lot object) throws SQLException;

    @Override
    protected abstract Lot prepareStatementForGetById(PreparedStatement statement) throws SQLException, DaoException;

    @Override
    protected abstract List<Lot> prepareStatementForGetAll(PreparedStatement statement) throws SQLException, DaoException;


    @Override
    public String getSelectQueryById() {
        return  "SELECT LOT.lot_id AS lot_id, LOT.lot_name AS lot_name, LOT.description AS description,LOT.is_blocked AS lot_is_approved, LOT.is_paid AS lot_is_paid,"
                + "            LOT.category_id AS category_name, LOT.image_path as image, AUCTION.auction_id as auction_id, AUCTION.start_time AS auction_start_time, AUCTION.finish_time AS auction_finish_time,"
                + "            LOT.user_id as seller_id, LOT.start_price as start_price, LOT.min_step AS min_step, "
                + "            SELLER.email AS seller_email, SELLER.login AS seller_login, SELLER.is_blocked AS seller_is_blocked, BID.bid_amount AS bid_price,"
                + "            BID.bid_id AS bid_id, BID.user_id AS bidder_id  FROM  auctionDB.lot LOT"
                + "    LEFT JOIN auctionDB.auction AUCTION ON LOT.auction_id = AUCTION.auction_id"
                + "    LEFT JOIN auctionDB.user SELLER ON LOT.user_id = SELLER.user_id"
                + "    LEFT JOIN auctionDB.category CATEGORY on LOT.category_id = CATEGORY.category_id"
                + "    LEFT JOIN auctionDB.bid BID ON LOT.lot_id = BID.lot_id WHERE LOT.lot_id = ? ORDER BY (CASE WHEN AUCTION.auction_type like 'direct' THEN bid_price END) DESC , "
                + "    (CASE WHEN AUCTION.auction_type like 'reverse' THEN bid_price END) ASC LIMIT 1";
    }


    @Override
    public String getSelectAllQuery() {
        return "SELECT LOT.id AS lot_id, LOT.name AS lot_name, LOT.description AS description,LOT.is_blocked AS lot_is_approved, LOT.is_paid AS lot_is_paid,"
                + "            LOT.category_name AS category_name,LOT.image_path as image, AUCTION.start_time AS auction_start_time, AUCTION.finish_time AS auction_finish_time,"
                + "            LOT.user_id as seller_id, LOT.start_price as start_price, "
                + "            SELLER.email AS seller_email, SELLER.login AS seller_login, SELLER.is_blocked AS seller_is_blocked, BID.bid_amount AS bid_price,"
                + "            BID.bid_id AS bid_id, BID.user_id AS bidder_id  FROM  auctionDB.lot LOT"
                + "    LEFT JOIN auctionDB.auction AUCTION ON LOT.auction_id = AUCTION.auction_id"
                + "    LEFT JOIN auctionDB.user SELLER ON LOT.user_id = SELLER.user_id"
                + "     LEFT JOIN auctionDB.category CATEGORY on LOT.category_id = CATEGORY.category_id"
                + "    LEFT JOIN auctionDB.bid BID ON LOT.lot_id = BID.lot_id ORDER BY (CASE WHEN auction_type = 0 THEN bid_price END) DESC , "
                + "    (CASE WHEN auction_type = 1 THEN bid_price END) ASC LIMIT 1)";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO auctionDB.lot(lot_name, description, category_id, auction_id, user_id, start_price, min_step, is_paid, is_blocked, image_path) VALUES (?, ?, ?, ?, ?, ?, ?, 0, 0, ?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE auctionDB.lot SET lot_name=?, description=?, category_id=?, auction_id=?, user_id=?, start_price=?, min_step=?, is_paid=?, is_blocked=? WHERE `lot_id`=?";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM auctionDB.lot WHERE lot_id=?";
    }

    @Override
    public String getDeleteByIdQuery() {
        return "DELETE FROM auctionDB.lot WHERE lot_id=?";
    }

    public abstract String getUnapproveLotQuery();

    public abstract String getApproveLotQuery();

    public abstract String getProposedLotSetQuery();

    public abstract String getCategoryNameQuery();

    public abstract String getUnfinishedLotQuery();

    public abstract String getAllLotsBySellerIdQuery();

    public abstract String getAllLotsByBidderIdQuery();

    public abstract String getSelectAllQueryLimit();

    public abstract String getSelectAllInAuctionQueryLimit();

    public abstract String getSelectIdLastProposedLot();

    public abstract boolean unApproveLot(int lotId) throws DaoException;

    public abstract boolean approveLot(int lotId) throws DaoException;

    public abstract int proposeLot(boolean isLotCreated) throws DaoException;

    public abstract List<Lot> takeProposedLotSet() throws DaoException;

    public abstract String takeCategoryNameById(int categoryId) throws DaoException;

    public abstract List<Lot> takeUnfinishedLotList() throws DaoException;

    public abstract List<Lot> takeLotsListBySellerId(int sellerId) throws DaoException;

    public abstract List<Lot> takeLotsListByBidderId(int bidderId) throws DaoException;

    public abstract List<Lot> takeAllLotsLimit(int startIndex, int quantity) throws DaoException, SQLException, ConnectionPoolException;

    public abstract List<Lot> takeAllLotsLimitInAuction(int auctionId, int startIndex, int quantity) throws DaoException, SQLException, ConnectionPoolException;

    protected abstract void commonData(PreparedStatement statement, Lot lot) throws SQLException;

    protected abstract void setLotParameters(Lot lot, ResultSet resultSet) throws SQLException;

    protected abstract List<Lot> setLotParametersForTakeLotsList(List<Lot> lots, ResultSet resultSet) throws SQLException;

    protected abstract List<Lot> parseResultSet(ResultSet resultSet, List<Lot> lots) throws DaoException;
}
