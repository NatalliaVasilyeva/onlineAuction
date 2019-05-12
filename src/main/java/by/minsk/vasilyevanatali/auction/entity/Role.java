package by.minsk.vasilyevanatali.auction.entity;

public enum Role {
    ADMIN("admin"),
    USER("user");

    private String name;

    Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return ordinal();
    }

    public static Role getById(Integer id) {
        return Role.values()[id-1];
    }

}
