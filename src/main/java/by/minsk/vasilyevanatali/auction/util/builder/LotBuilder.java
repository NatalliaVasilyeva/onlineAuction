package by.minsk.vasilyevanatali.auction.util.builder;


import by.minsk.vasilyevanatali.auction.entity.Category;
import by.minsk.vasilyevanatali.auction.entity.Lot;

import java.time.LocalDateTime;

public class LotBuilder {
    private int lotId;
    private String name;
    private String description;
    private Category category;
    private int auctionId;
    private LocalDateTime startTime;
    private LocalDateTime finishTime;
    private int sellerId;
    private String sellerEmail;
    private String sellerLogin;
    private int sellerIsBlocked;
    private int startPrice;
    private int minimumStep;
    private boolean isPaid;
    private int isBlocked;
    private int bidId;
    private int bidderId;
    private int bidPrice;

    public LotBuilder() {

    }

    /**
     * Sets lot id according to given parameter.
     *
     * @param lotId lot id.
     * @return current builder.
     */
    public LotBuilder withId(int lotId) {
        this.lotId = lotId;
        return this;
    }

    /**
     * Sets lot name according to given parameter.
     *
     * @param name of lot.
     * @return current builder.
     */
    public LotBuilder withLotName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Sets description according to given parameter.
     *
     * @param description of lot.
     * @return current builder.
     */
    public LotBuilder withLotDescription(String description) {
        this.description = description;
        return this;
    }


    /**
     * Set's category according to given parameter.
     *
     * @param category of lot.
     * @return current builder.
     */
    public LotBuilder withLotCategory(Category category) {
        this.category = category;
        return this;
    }

    /**
     * Take auction id according to given parameter.
     *
     * @param auctionId for lot.
     * @return current builder.
     */
    public LotBuilder withAuctionId(int auctionId) {
        this.auctionId = auctionId;
        return this;
    }

    /**
     * Set's start time according to given parameter.
     *
     * @param startTime of lot.
     * @return current builder.
     */
    public LotBuilder withLotStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    /**
     * Set's finish time according to given parameter.
     *
     * @param finishTime of lot.
     * @return current builder.
     */
    public LotBuilder withLotFinishTime(LocalDateTime finishTime) {
        this.finishTime = finishTime;
        return this;
    }

    /**
     * Take seller id according to given parameter.
     *
     * @param sellerId who sell the lot.
     * @return current builder.
     */
    public LotBuilder withSellerId(int sellerId) {
        this.sellerId = sellerId;
        return this;
    }


    /**
     * Take seller email according to given parameter.
     *
     * @param sellerEmail who sell the lot.
     * @return current builder.
     */
    public LotBuilder withSellerEmail(String sellerEmail) {
        this.sellerEmail = sellerEmail;
        return this;
    }

    /**
     * Take seller login according to given parameter.
     *
     * @param sellerLogin who sell the lot.
     * @return current builder.
     */
    public LotBuilder withSellerLogin(String sellerLogin) {
        this.sellerLogin = sellerLogin;
        return this;
    }


    /**
     * Take is seller blocked according to given parameter.
     *
     * @param sellerIsBlocked who sell the lot.(0 - is blocked, 1 - not blocked)
     * @return current builder.
     */
    public LotBuilder withIsSellerBlocked(int sellerIsBlocked) {
        this.sellerIsBlocked = sellerIsBlocked;
        return this;
    }

    /**
     * Sets start price of lot according to given parameter.
     *
     * @param startPrice of lot
     * @return current builder.
     */
    public LotBuilder withStartPrice(int startPrice) {
        this.startPrice = startPrice;
        return this;
    }

    /**
     * Sets minimum step of price lot according to given parameter.
     *
     * @param minimumStep of lot's price
     * @return current builder.
     */
    public LotBuilder withMinimumStep(int minimumStep) {
        this.minimumStep = minimumStep;
        return this;
    }

    /**
     * Sets is lot paid according to given parameter.
     *
     * @param isPaid lot
     * @return current builder.
     */
    public LotBuilder isLotPaid(boolean isPaid) {
        this.isPaid = isPaid;
        return this;
    }


    /**
     * Sets is lot blocked according to given parameter.
     *
     * @param isBlocked lot (0 - is blocked, 1 - not blocked)
     * @return current builder.
     */
    public LotBuilder withIsLotBlocked(int isBlocked) {
        this.isBlocked = isBlocked;
        return this;
    }


    /**
     * Take bid id for current lot according to given parameter.
     *
     * @param bidId for lot
     * @return current builder.
     */
    public LotBuilder withBidId(int bidId) {
        this.bidId = bidId;
        return this;
    }


    /**
     * Take biddder id who make a bid according to given parameter.
     *
     * @param bidderId for lot
     * @return current builder.
     */
    public LotBuilder withBidderId(int bidderId) {
        this.bidderId = bidderId;
        return this;
    }


    /**
     * Sets bid price for lot according to given parameter.
     *
     * @param bidPrice for lot
     * @return current builder.
     */
    public LotBuilder withBidPrice(int bidPrice) {
        this.bidPrice = bidPrice;
        return this;
    }

    /**
     * Builds and returns {@link Lot} object in accordance with set earlier
     * parameters (lotId, name, description, category, auctionId, startTime, finishTime, sellerId, sellerEmail, sellerLogin, sellerIsBlocked, startPrice, minimumStep,
     * isPaid, isBlocked, bidId, bidderId, bidPrice).
     *
     * @return object with information about new bid.
     */
    public Lot build() {
        Lot lot = new Lot();
        lot.setId(lotId);
        lot.setName(name);
        lot.setDescription(description);
        lot.setCategory(category);
        lot.setAuctionId(auctionId);
        lot.setStartTime(startTime);
        lot.setFinishTime(finishTime);
        lot.setSellerId(sellerId);
        lot.setSellerEmail(sellerEmail);
        lot.setSellerLogin(sellerLogin);
        lot.setSellerIsBlocked(sellerIsBlocked);
        lot.setStartPrice(startPrice);
        lot.setMinimumStep(minimumStep);
        lot.setPaid(isPaid);
        lot.setIsBlocked(isBlocked);
        lot.setBidId(bidId);
        lot.setBidderId(bidderId);
        lot.setBidPrice(bidPrice);
        return lot;
    }

    int getId() {
        return lotId;
    }

    String getName() {
        return name;
    }

    String getDescription() {
        return description;
    }

    Category getCategory() {
        return category;
    }

    int getAuctionId() {
        return auctionId;
    }

    LocalDateTime getStartTime() {
        return startTime;
    }

    LocalDateTime getFinishTime() {
        return finishTime;
    }

    int getSellerId() {
        return sellerId;
    }

    String getSellerEmail() {
        return sellerEmail;
    }

    String getSellerLogin() {
        return sellerLogin;
    }

    int getSellerIsBlocked() {
        return sellerIsBlocked;
    }

    int getStartPrice() {
        return startPrice;
    }

    int getMinimumStep() {
        return minimumStep;
    }

    boolean isPaid() {
        return isPaid;
    }

    int getIsBlocked() {
        return isBlocked;
    }

    int getBidId() {
        return bidId;
    }

    int getBidderId() {
        return bidderId;
    }

    int getBidPrice() {
        return bidPrice;
    }
}
