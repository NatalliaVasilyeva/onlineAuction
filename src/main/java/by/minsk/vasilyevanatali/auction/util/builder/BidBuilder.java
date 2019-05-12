package by.minsk.vasilyevanatali.auction.util.builder;


import by.minsk.vasilyevanatali.auction.entity.Bid;

public class BidBuilder {

    private int bidId;
    private int bidAmount;
    private int userId;
    private int lotId;

    public BidBuilder() {

    }

    /**
     * Sets bid id according to given parameter.
     *
     * @param bidId bid id.
     * @return current builder.
     */
    public BidBuilder withId(int bidId) {
        this.bidId = bidId;
        return this;
    }

    /**
     * Sets bid amount according to given parameter.
     *
     * @param bidAmount for lot.
     * @return current builder.
     */
    public BidBuilder withBidAmount(int bidAmount) {
        this.bidAmount = bidAmount;
        return this;
    }

    /**
     * Take user id according to given parameter.
     *
     * @param userId who make a bid.
     * @return current builder.
     */
    public BidBuilder byUserWithId(int userId) {
        this.userId = userId;
        return this;
    }

    /**
     * Take lot id according to given parameter.
     *
     * @param lotId to make a bid.
     * @return current builder.
     */
    public BidBuilder forLotWithId(int lotId) {
        this.lotId = lotId;
        return this;
    }


    /**
     * Builds and returns {@link Bid} object in accordance with set earlier
     * parameters (bidId, bidAmount, userId, lotId).
     *
     * @return object with information about new bid.
     */
    public Bid build() {
        Bid bid = new Bid();
        bid.setId(bidId);
        bid.setBidAmount(bidAmount);
        bid.setUserId(userId);
        bid.setLotId(lotId);
        return bid;
    }

    int getId() {
        return bidId;
    }

    int getBidAmount() {
        return bidAmount;
    }

    int getUserId() {
        return userId;
    }

    int getLotId() {
        return lotId;
    }
}
