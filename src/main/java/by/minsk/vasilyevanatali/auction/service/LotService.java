package by.minsk.vasilyevanatali.auction.service;


import by.minsk.vasilyevanatali.auction.connection.exception.ConnectionPoolException;
import by.minsk.vasilyevanatali.auction.dao.DaoFactory;
import by.minsk.vasilyevanatali.auction.dao.exception.DaoException;
import by.minsk.vasilyevanatali.auction.dao.impl.AuctionDao;
import by.minsk.vasilyevanatali.auction.dao.impl.BidDao;
import by.minsk.vasilyevanatali.auction.dao.impl.LotDao;
import by.minsk.vasilyevanatali.auction.dao.impl.UserDao;
import by.minsk.vasilyevanatali.auction.entity.*;
import by.minsk.vasilyevanatali.auction.service.exception.ServiceException;
import by.minsk.vasilyevanatali.auction.util.builder.BidBuilder;
import by.minsk.vasilyevanatali.auction.util.builder.LotBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class LotService {
    private static final Logger LOGGER = LogManager.getLogger(LotService.class);
    private DaoFactory daoFactory = DaoFactory.getInstance();

    BidBuilder bidBuilder = new BidBuilder();
    LotBuilder lotBuilder = new LotBuilder();

    public Optional<List<Lot>> takeLotLimit(int startIndex, int maxQuantity) throws ServiceException {
        LOGGER.debug("Start index:" + startIndex + " Quantity:" + maxQuantity);
        Optional<List<Lot>> optionalLotSet = Optional.empty();
        LotDao lotDao = daoFactory.getLotDao();
        try {
            optionalLotSet = Optional.of(lotDao.takeAllLotsLimit(startIndex, maxQuantity));
            LOGGER.debug("Return " + optionalLotSet.get().size() + " lot entities");
        } catch (DaoException | SQLException | ConnectionPoolException e) {
            throw new ServiceException(e);
        }
        return optionalLotSet;
    }

    public Optional<List<Lot>> takeLotSetInAuction(int id, int startIndex, int maxQuantity) throws ServiceException {
        LOGGER.debug("Request for lot set in auction: " + id + " starting from index:" + startIndex
                + " with quantity of up to:" + maxQuantity);
        Optional<List<Lot>> optionalLotSet;
        LotDao lotDao = daoFactory.getLotDao();
        try {
            optionalLotSet = Optional.of(lotDao.takeAllLotsLimitInAuction(id, startIndex, maxQuantity));
            LOGGER.debug("Returns " + optionalLotSet.get().size() + " lot entities");
        } catch (ConnectionPoolException | DaoException | SQLException e) {
            throw new ServiceException(e);
        }
        return optionalLotSet;
    }

    public Optional<List<Lot>> takeLotSetBySellerId(int sellerId) throws ServiceException {
        LOGGER.debug("Request for lot set for current user as seller");
        Optional<List<Lot>> optionalLotSet;
        LotDao lotDao = daoFactory.getLotDao();
        try {
            optionalLotSet = Optional.of(lotDao.takeLotsListBySellerId(sellerId));
            LOGGER.debug("Returns " + optionalLotSet.get().size() + " lot entities");
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return optionalLotSet;
    }

    public Optional<List<Lot>> takeLotSetByBidderId(int bidderId) throws ServiceException {
        LOGGER.debug("Request for lot set for current user as bidder");
        Optional<List<Lot>> optionalLotSet;
        LotDao lotDao = daoFactory.getLotDao();
        try {
            optionalLotSet = Optional.of(lotDao.takeLotsListByBidderId(bidderId));
            LOGGER.debug("Returns " + optionalLotSet.get().size() + " lot entities");
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return optionalLotSet;
    }


    public Optional<Lot> findLotById(int lotId) throws ServiceException {
        LOGGER.debug("Requested lot with id =" + lotId);
        Optional<Lot> optionalLot = Optional.empty();
        LotDao lotDao = daoFactory.getLotDao();
        try {
            optionalLot = lotDao.findById(lotId);
            LOGGER.debug("Lot " + (optionalLot.isPresent() ? "found" : "not found"));
        } catch (DaoException | SQLException e) {
            throw new ServiceException(e);
        }
        return optionalLot;
    }

    public User placeBid(int lotId, int amount, int bidderId) throws ServiceException {
        LOGGER.debug("Service recive bid for lot with id: " + lotId + ", with price:" + amount + ", by user with id:" + bidderId);

        User newBidderState = null;
        BidDao bidDao = daoFactory.getBidDao();
        UserDao userDao = daoFactory.getUserDao();
        try {
            bidDao.startTransaction();
            Optional<User> userOptional = userDao.findById(bidderId);
            if (!userOptional.isPresent()) {
                throw new ServiceException("Bidder with id = " + bidderId + " not found");
            }
            User bidder = userOptional.get();
            Optional<Lot> lotOptional = findLotById(lotId);
            if (!lotOptional.isPresent()) {
                throw new ServiceException("Lot with id = " + lotId + " not found");
            }
            Lot lot = lotOptional.get();
            Optional<User> previousBidderOptional = userDao.findById(lot.getBidderId());
            Optional<User> sellerOptional = userDao.findById(lot.getSellerId());
            if (!userOptional.isPresent()) {
                throw new ServiceException("Lot seller with id = " + bidderId + " not found");
            }
            User seller = sellerOptional.get();
            if (checkBidValidity(lot, bidder, seller, amount)) {
                LOGGER.trace("Perform bid on " + lot.getAuctionId() + " auction");
                AuctionType auctionType = takeAuctionTypeFromLot(lot);
                if (auctionType == AuctionType.DIRECT) {
                    bidder.setFrozenMoney(bidder.getFrozenMoney() + amount);
                    bidder.setBalance(bidder.getBalance() - amount);
                    userDao.update(bidder);
                    LOGGER.trace("Bid amount on users account had been frozen.");

                    if (previousBidderOptional.isPresent() && previousBidderOptional.get().getId() != seller.getId()) {
                        LOGGER.trace("Previous bidder found.");
                        User previousBidder = previousBidderOptional.get();
                        previousBidder.setBalance(previousBidder.getBalance() + (lot.getBidPrice()));
                        previousBidder.setFrozenMoney(previousBidder.getFrozenMoney() - (lot.getBidPrice()));
                        userDao.update(previousBidder);
                        LOGGER.trace("Previous bidder's bid amount returned on his account.");
                    }
                } else {
                    seller.setFrozenMoney(seller.getFrozenMoney() - (lot.getBidPrice() - (amount)));
                    seller.setBalance(seller.getBalance() + (lot.getBidPrice() + (amount)));
                    userDao.update(seller);
                }
                LOGGER.trace("Place bid");
                Bid bid = bidBuilder.withBidAmount(amount)
                        .byUserWithId(bidder.getId())
                        .forLotWithId(lot.getId())
                        .build();
                LOGGER.trace("Bid was placed");


//                LOGGER.trace("Place bid");
//                Bid bid = new Bid();
//                bid.setBidAmount(amount);
//                bid.setUserId(bidder.getId());
//                bid.setLotId(lot.getId());
//                bidDao.create(bid);
//                LOGGER.trace("Bid was placed");
                newBidderState = bidder;
                bidDao.finishTransaction();
            }

        } catch (DaoException | SQLException e) {
            try {
                bidDao.rollbackTransaction();
            } catch (DaoException e1) {
                e1.printStackTrace();
            }
            throw new ServiceException(e);
        }

        return newBidderState;
    }

    private boolean checkBidValidity(Lot lot, User bidder, User seller, int amount) throws SQLException, DaoException {
        boolean isValidBid = lot != null
                && bidder != null
                && seller != null
                && isNotSameUser(bidder, seller)
                && isNotPreviousBidder(lot, bidder)
                && isActiveLot(lot)
                && (isSufficientDirectAuctionBid(lot, bidder, amount)
                || isSufficientReverseAuctionBid(lot, seller, amount));
        LOGGER.trace("This bid " + (isValidBid ? "is" : "isn't") + " valid");
        return isValidBid;
    }


    private boolean isNotSameUser(User bidder, User seller) {
        boolean isNotSameUser = bidder != null
                && seller != null
                && bidder.getId() != seller.getId();
        LOGGER.trace("This " + (!isNotSameUser ? "is" : "isn't") + " same user");
        return isNotSameUser;
    }

    private boolean isNotPreviousBidder(Lot lot, User bidder) {
        boolean isNotPreviousBidder = lot != null
                && bidder != null
                && lot.getBidderId() != bidder.getId();
        LOGGER.trace("This " + (!isNotPreviousBidder ? "is" : "isn't") + " previous bidder user");
        return isNotPreviousBidder;
    }


    private boolean isActiveLot(Lot lot) {
        boolean isActiveLot = lot != null
                && lot.getIsBlocked() != 0
                && lot.getStartTime() != null
                && lot.getStartTime().isBefore(LocalDateTime.now())
                && lot.getFinishTime() != null
                && lot.getFinishTime().isAfter(LocalDateTime.now())
                && lot.isSellerIsBlocked() != 0;
        LOGGER.trace("This lot " + (isActiveLot ? "is" : "isn't") + " active");
        return isActiveLot;
    }

    private boolean isSufficientDirectAuctionBid(Lot lot, User bidder, int amount) throws SQLException, DaoException {

        AuctionType auctionType = takeAuctionTypeFromLot(lot);
        boolean isSufficientBid = auctionType == AuctionType.DIRECT
                && bidder.getBalance() != 0
                && bidder.getBalance() >= amount
                && amount > lot.getBidPrice();
        LOGGER.trace("This bid " + (isSufficientBid ? "is" : "isn't") + " sufficient for direct auction");
        return isSufficientBid;
    }

    private boolean isSufficientReverseAuctionBid(Lot lot, User seller, int amount) throws SQLException, DaoException {
        AuctionType auctionType = takeAuctionTypeFromLot(lot);
        boolean isSufficientBid = auctionType == AuctionType.REVERSE
                && seller.getBalance() != 0
                && (seller.getBalance() + (lot.getBidPrice())) >= amount
                && amount < lot.getBidPrice();
        LOGGER.trace("This bid " + (isSufficientBid ? "is" : "isn't") + " sufficient for reverse auction");
        return isSufficientBid;
    }


    public Optional<List<Lot>> findProposedLotSet() throws ServiceException {
        Optional<List<Lot>> proposedLotSet = Optional.empty();
        LotDao lotDao = daoFactory.getLotDao();
        try {
            proposedLotSet = Optional.ofNullable(lotDao.takeProposedLotSet());
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return proposedLotSet;
    }


    public String takeCategoryName(int categoryId) throws ServiceException {
        LotDao lotDao = daoFactory.getLotDao();
        String categoryName;
        try {
            categoryName = lotDao.takeCategoryNameById(categoryId + 1);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return categoryName;
    }


    public int proposeLot(String lotName, String lotDescription, int categoryId, int auctionId, int sellerId, int startPrice, int minimumStep) throws ServiceException {

        Integer lotId = null;
        Optional<User> optionalUser = Optional.empty();
        BidDao bidDao = daoFactory.getBidDao();
        UserDao userDao = daoFactory.getUserDao();
        AuctionDao auctionDao = daoFactory.getAuctionDao();
        LotDao lotDao = daoFactory.getLotDao();
        try {
            bidDao.startTransaction();
            optionalUser = userDao.findById(sellerId);
            if (optionalUser.isPresent() && optionalUser.get().getIsBlocked() != 0) {
                LOGGER.debug("Owner with active account found");
                Optional<Auction> optionalAuction = Optional.empty();
                optionalAuction = auctionDao.findById(auctionId);

                if (optionalAuction.isPresent() && optionalAuction.get().getFinishTime().isAfter(LocalDateTime.now())
                        && (optionalAuction.get().getAuctionType() == AuctionType.DIRECT)) {
                    LOGGER.debug("Active auction found");
                    // fix
                    String categoryName = takeCategoryName(categoryId);
                    Category category = Category.valueOf(categoryName.toUpperCase());
                    ///
                    if (category != null) {
                        LOGGER.debug("Id of proposed category is present");
//                        Lot lot = new Lot();
//                        lot.setName(lotName);
//                        lot.setDescription(lotDescription);
//                        lot.setCategory(category);
//                        lot.setAuctionId(auctionId);
//                        lot.setSellerId(sellerId);
//                        lot.setStartPrice(startPrice);
//                        lot.setMinimumStep(minimumStep);
//

                        Lot lot = lotBuilder.withLotName(lotName)
                                .withLotDescription(lotDescription)
                                .withLotCategory(category)
                                .withAuctionId(auctionId)
                                .withSellerId(sellerId)
                                .withStartPrice(startPrice)
                                .withMinimumStep(minimumStep)
                                .build();
                        boolean isCreated = lotDao.create(lot);
                        lotId = lotDao.proposeLot(isCreated);
                        if (lotId != null) {
//                            Bid bid = new Bid();
//                            bid.setBidAmount(startPrice);
//                            bid.setLotId(lotId);
//                            bid.setUserId(sellerId);


                            Bid bid = bidBuilder.withBidAmount(startPrice)
                                    .forLotWithId(lotId)
                                    .byUserWithId(sellerId)
                                    .build();

                            bidDao.create(bid);
                        }
                        if (optionalAuction.get().getAuctionType() == AuctionType.REVERSE) {
                            User owner = optionalUser.get();
                            owner.setFrozenMoney(owner.getFrozenMoney() + (startPrice));
                            owner.setBalance(owner.getBalance() - (startPrice));
                            userDao.update(owner);
                        }
                        LOGGER.debug("Lot was created with Id = " + lotId);
                    } else {
                        LOGGER.debug("Id of proposed category not found");
                    }
                } else {
                    LOGGER.debug("Active auction not found");
                }
            } else {
                LOGGER.debug("Active owner didn't exists");
            }
            bidDao.finishTransaction();
        } catch (DaoException | SQLException e) {
            try {
                bidDao.rollbackTransaction();
            } catch (DaoException e1) {
                e1.printStackTrace();
                throw new ServiceException(e);
            }
            throw new ServiceException(e);
        }
        return lotId;
    }

    public boolean approveLot(Integer lotId) throws ServiceException {
        LotDao lotDao = daoFactory.getLotDao();
        boolean isLotApproved = false;
        try {
            isLotApproved = lotDao.approveLot(lotId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return isLotApproved;
    }

    public boolean unApproveLot(Integer lotId) throws ServiceException {
        LotDao lotDao = daoFactory.getLotDao();
        boolean isLotUnApproved = false;
        try {
            isLotUnApproved = lotDao.unApproveLot(lotId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return isLotUnApproved;
    }

    public boolean editLot(Integer lotId, String lotName, String lotDescription, String categoryName) throws ServiceException {
        LotDao lotDao = daoFactory.getLotDao();
        boolean isLotEdit = false;
        try {
            Category category = Category.valueOf(categoryName.toUpperCase());
            Lot lot = findLotById(lotId).get();
            lot.setName(lotName);
            lot.setDescription(lotDescription);
            lot.setCategory(category);
            isLotEdit = lotDao.editLot(lot);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return isLotEdit;
    }

    public void deleteLot(Integer lotId) throws ServiceException {
        LotDao lotDao = daoFactory.getLotDao();
        try {
            lotDao.deleteById(lotId);
        } catch (DaoException | SQLException e) {
            throw new ServiceException(e);
        }

    }

    private AuctionType takeAuctionTypeFromLot(Lot lot) throws SQLException, DaoException {
        int auctionId = lot.getAuctionId();
        AuctionDao auctionDao = daoFactory.getAuctionDao();
        Optional<Auction> auctionOptional = auctionDao.findById(auctionId);
        Auction auction = auctionOptional.get();
        return auction.getAuctionType();
    }
}
