package by.minsk.vasilyevanatali.auction.entity;

public enum Category {
    CAR("car"), PICTURE("picture"), COMPUTER("computer"), PHONE("phone");

    private String name;

    Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return ordinal();
    }

    public static Category getById(Integer id) {

        //TODO
        return Category.values()[id-1];
    }
}
