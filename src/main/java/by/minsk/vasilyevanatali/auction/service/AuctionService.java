package by.minsk.vasilyevanatali.auction.service;


import by.minsk.vasilyevanatali.auction.dao.DaoFactory;
import by.minsk.vasilyevanatali.auction.dao.exception.DaoException;
import by.minsk.vasilyevanatali.auction.dao.impl.AuctionDao;
import by.minsk.vasilyevanatali.auction.entity.Auction;
import by.minsk.vasilyevanatali.auction.entity.AuctionType;
import by.minsk.vasilyevanatali.auction.entity.User;
import by.minsk.vasilyevanatali.auction.service.exception.ServiceException;
import by.minsk.vasilyevanatali.auction.util.builder.AuctionBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class AuctionService {
    private DaoFactory daoFactory = DaoFactory.getInstance();

    private static final Logger LOGGER = LogManager.getLogger(AuctionService.class);
    AuctionBuilder auctionBuilder = new AuctionBuilder();

    public Optional<List<Auction>> getAllAuction() throws ServiceException {
        LOGGER.debug("Service looking for lot entities");
        Optional<List<Auction>> optionalAuctionList = Optional.empty();
        AuctionDao auctionDao = daoFactory.getAuctionDao();
        try {
            optionalAuctionList = Optional.of(auctionDao.findAll());
            LOGGER.debug("Service returns optional auction entities");
        } catch (DaoException | SQLException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return optionalAuctionList;
    }

    public boolean createAuction(LocalDateTime startTime, LocalDateTime finishTime, AuctionType type, String description, Integer ownerId) throws ServiceException {
        LOGGER.debug("Service creates auction:");
        boolean isAuctionCreated = false;
        AuctionDao auctionDao = daoFactory.getAuctionDao();
        try {
            Auction auction = auctionBuilder.withStartTime(startTime)
                    .withFinishTime(finishTime)
                    .withAuctionType(type)
                    .withDescription(description)
                    .byOwnerId(ownerId)
                    .build();
//            Auction auction = new Auction(startTime, finishTime, type, description);
            isAuctionCreated = auctionDao.create(auction);
            LOGGER.debug("Auction was created");
        } catch (SQLException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return isAuctionCreated;
    }


    public Optional<Auction> findAuctionById(int auctionId) throws ServiceException {
        Optional<Auction> auctionOptional = Optional.empty();
        AuctionDao auctionDao = daoFactory.getAuctionDao();
        try {
            auctionOptional = auctionDao.findById(auctionId);
            LOGGER.debug(auctionOptional + " was found");
        } catch (DaoException | SQLException e) {
            throw new ServiceException(e.getMessage(), e);
        }

        return auctionOptional;
    }


    public Optional<List<Auction>> getAllUsersAuction(User user) throws ServiceException {
        LOGGER.debug("Service looking for auctions entities");
        Optional<List<Auction>> optionalAuctionList = Optional.empty();
        AuctionDao auctionDao = daoFactory.getAuctionDao();
        try {
            optionalAuctionList = Optional.ofNullable(auctionDao.getUsersAllAuctions(user.getId()));
            LOGGER.debug("Service returns optional auction entities");
        } catch (DaoException | SQLException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return optionalAuctionList;
    }

    public Optional<Integer> getMaxIdOfUserAuctions(User user) throws ServiceException {
        LOGGER.debug("Service looking for maximum id of users auction");
        Optional<Integer> optionalMaxIAuctionId = Optional.empty();
        AuctionDao auctionDao = daoFactory.getAuctionDao();
        try {
            optionalMaxIAuctionId = Optional.ofNullable(auctionDao.getMaxIdOfUserAuctions(user.getId()));
            LOGGER.debug("Service returns optional max auction id of this user");
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return optionalMaxIAuctionId;
    }

    public boolean updateAuction(Integer auctionId, LocalDateTime startTime, LocalDateTime finishTime, String description) throws ServiceException {
        LOGGER.debug("Service update auction:");
        boolean isAuctionCreated = false;
        AuctionDao auctionDao = daoFactory.getAuctionDao();
        try {
            Auction auction = findAuctionById(auctionId).get();
            auction.setStartTime(startTime);
            auction.setFinishTime(finishTime);
            auction.setDescription(description);

            isAuctionCreated = auctionDao.update(auction);
            LOGGER.debug("Auction was updated");
        } catch (SQLException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return isAuctionCreated;
    }


    public boolean deleteAuctionById(Integer auctionId) throws ServiceException {
        LOGGER.debug("Service delete auction:");
        boolean isAuctionDelete = false;
        AuctionDao auctionDao = daoFactory.getAuctionDao();
        try {
            isAuctionDelete = auctionDao.deleteById(auctionId);
            LOGGER.debug("Auction was deleted");
        } catch (SQLException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return isAuctionDelete;
    }


}
