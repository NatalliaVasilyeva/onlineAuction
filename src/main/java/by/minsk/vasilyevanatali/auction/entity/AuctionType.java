package by.minsk.vasilyevanatali.auction.entity;

public enum AuctionType {
    DIRECT("direct"),
    REVERSE("reverse");

    private String name;

    AuctionType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return ordinal();
    }

    public static AuctionType getById(Integer id) {

        //TODO
        return AuctionType.values()[id - 1];
    }
}
