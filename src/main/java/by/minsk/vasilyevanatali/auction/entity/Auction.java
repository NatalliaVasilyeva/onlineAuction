package by.minsk.vasilyevanatali.auction.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Auction extends Bean {
    private LocalDateTime startTime;
    private LocalDateTime finishTime;
    private AuctionType auctionType;
    private String description;
    private Integer owner_id;

    private List<Lot> lots = new ArrayList<>();

    public Auction() {
    }

    public Auction(int id, LocalDateTime startTime, LocalDateTime finishTime, AuctionType auctionType, String description, Integer owner_id) {
        super(id);
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.auctionType = auctionType;
        this.description = description;
        this.owner_id = owner_id;
    }

    public Auction(int id, LocalDateTime startTime, LocalDateTime finishTime, AuctionType auctionType, Integer owner_id) {
        super(id);
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.auctionType = auctionType;
        this.owner_id = owner_id;
    }

    public Auction(LocalDateTime startTime, LocalDateTime finishTime, AuctionType auctionType, String description, Integer owner_id) {
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.auctionType = auctionType;
        this.description = description;
        this.owner_id = owner_id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(LocalDateTime finishTime) {
        this.finishTime = finishTime;
    }

    public AuctionType getAuctionType() {
        return auctionType;
    }

    public void setAuctionType(AuctionType auctionType) {
        this.auctionType = auctionType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Lot> getLots() {
        return lots;
    }

    public void setLots(List<Lot> lots) {
        this.lots = lots;
    }

    public Integer getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(Integer owner_id) {
        this.owner_id = owner_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Auction auction = (Auction) o;
        return (auctionType == auction.auctionType &&
                startTime.equals(auction.startTime) &&
                finishTime.equals(auction.finishTime) &&
                description.equals(auction.description)) &&
                owner_id == auction.owner_id &&
                lots.equals(auction.lots);
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + (auctionType != null ? auctionType.hashCode() : 0);
        result = prime * result + (startTime != null ? startTime.hashCode() : 0);
        result = prime * result + (finishTime != null ? finishTime.hashCode() : 0);
        result = prime * result + (description != null ? description.hashCode() : 0);
        result = prime * result + owner_id;
        result = prime * result + (lots != null ? lots.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Auction{" +
                "startTime=" + startTime +
                ", finishTime=" + finishTime +
                ", auctionType=" + auctionType +
                ", description='" + description + '\'' +
                ", owner id='" + owner_id + '\'' +
                ", lots=" + lots +
                '}';
    }
}
