package by.minsk.vasilyevanatali.auction.util.builder;


import by.minsk.vasilyevanatali.auction.entity.Credit;

import java.time.LocalDateTime;

public class CreditBuilder {
    private int creditId;
    private int userId;
    private int debtSum;
    private LocalDateTime recievedDate;
    private LocalDateTime paymentDate;
    private int creditPercent;

    public CreditBuilder() {

    }

    /**
     * Sets credit id according to given parameter.
     *
     * @param creditId credit id.
     * @return current builder.
     */
    public CreditBuilder withId(int creditId) {
        this.creditId = creditId;
        return this;
    }

    /**
     * Take user id according to given parameter.
     *
     * @param userId who take a credit.
     * @return current builder.
     */
    public CreditBuilder forUserWithId(int userId) {
        this.userId = userId;
        return this;
    }

    /**
     * Sets debt sum according to given parameter.
     *
     * @param debtSum for user wha take a credit.
     * @return current builder.
     */
    public CreditBuilder withBidAmount(int debtSum) {
        this.debtSum = debtSum;
        return this;
    }


    /**
     * Set's recieved date  according to given parameter.
     *
     * @param recievedDate of take a credit.
     * @return current builder.
     */
    public CreditBuilder withRecievedDate(LocalDateTime recievedDate) {
        this.recievedDate = recievedDate;
        return this;
    }

    /**
     * Set's payment date  according to given parameter.
     *
     * @param paymentDate of pay the credit.
     * @return current builder.
     */
    public CreditBuilder withPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
        return this;
    }

    /**
     * Set's percent of credit  according to given parameter.
     *
     * @param creditPercent of  credit.
     * @return current builder.
     */
    public CreditBuilder withPaymentDate(int creditPercent) {
        this.creditPercent = creditPercent;
        return this;
    }

    /**
     * Builds and returns {@link Credit} object in accordance with set earlier
     * parameters (creditId, userId, debtSum, recievedDate, paymentDate, creditPercent ).
     *
     * @return object with information about new bid.
     */
    public Credit build() {
        Credit credit = new Credit();
        credit.setId(creditId);
        credit.setUserId(userId);
        credit.setDebtSum(debtSum);
        credit.setRecievedDate(recievedDate);
        credit.setPaymentDate(paymentDate);
        credit.setCreditPercent(creditPercent);
        return credit;
    }

    int getId() {
        return creditId;
    }

    int getUserId() {
        return userId;
    }

    int getDebtSum() {
        return debtSum;
    }

    LocalDateTime getRecievedDate() {
        return recievedDate;
    }

    LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    int getCreditPercent() {
        return creditPercent;
    }
}
