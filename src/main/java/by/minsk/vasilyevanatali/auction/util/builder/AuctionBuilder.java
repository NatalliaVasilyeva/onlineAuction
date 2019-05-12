package by.minsk.vasilyevanatali.auction.util.builder;


import by.minsk.vasilyevanatali.auction.entity.Auction;
import by.minsk.vasilyevanatali.auction.entity.AuctionType;

import java.time.LocalDateTime;

public class AuctionBuilder {
    private int auctionId;
    private LocalDateTime startTime;
    private LocalDateTime finishTime;
    private AuctionType auctionType;
    private String description;

    public AuctionBuilder() {

    }

    /**
     * Sets auction id according to given parameter.
     *
     * @param auctionId auction id.
     * @return current builder.
     */
    public AuctionBuilder withId(int auctionId) {
        this.auctionId = auctionId;
        return this;
    }

    /**
     * Sets start time according to given parameter.
     *
     * @param startTime of each auction.
     * @return current builder.
     */
    public AuctionBuilder withStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    /**
     * Sets finish time according to given parameter.
     *
     * @param finishTime of each auction.
     * @return current builder.
     */
    public AuctionBuilder withFinishTime(LocalDateTime finishTime) {
        this.finishTime = finishTime;
        return this;
    }

    /**
     * Sets auction type according to given parameter.
     *
     * @param auctionType of each auction.
     * @return current builder.
     */
    public AuctionBuilder withAuctionType(AuctionType auctionType) {
        this.auctionType = auctionType;
        return this;
    }

    /**
     * Sets description according to given parameter.
     *
     * @param description of auction.
     * @return current builder.
     */
    public AuctionBuilder withDescription(String description) {
        this.description = description;
        return this;
    }


    /**
     * Builds and returns {@link Auction} object in accordance with set earlier
     * parameters (id, startTime, finishTime, auctionType, description).
     *
     * @return object with information about new auction.
     */
    public Auction build() {
        Auction auction = new Auction();
        auction.setId(auctionId);
        auction.setStartTime(startTime);
        auction.setFinishTime(finishTime);
        auction.setAuctionType(auctionType);
        auction.setDescription(description);
        return auction;
    }

    int getId() {
        return auctionId;
    }

    LocalDateTime getStartTime() {
        return startTime;
    }

    LocalDateTime getFinishTime() {
        return finishTime;
    }

    AuctionType getAuctionType() {
        return auctionType;
    }

    String getDescription() {
        return description;
    }


}
