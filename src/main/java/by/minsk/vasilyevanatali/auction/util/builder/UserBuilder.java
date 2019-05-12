package by.minsk.vasilyevanatali.auction.util.builder;


import by.minsk.vasilyevanatali.auction.entity.Role;
import by.minsk.vasilyevanatali.auction.entity.User;

public class UserBuilder {
    private int userId;
    private Role role;
    private String login;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private int balance;
    private int frozenMoney;
    private int isBlocked;

    public UserBuilder() {

    }

    /**
     * Sets user id according to given parameter.
     *
     * @param userId user id.
     * @return current builder.
     */
    public UserBuilder withUserId(int userId) {
        this.userId = userId;
        return this;
    }

    /**
     * Sets user role according to given parameter.
     *
     * @param role user role.
     * @return current builder.
     */
    public UserBuilder withUserRole(Role role) {
        this.role = role;
        return this;
    }

    /**
     * Sets login according to given parameter.
     *
     * @param login user's login.
     * @return current builder.
     */
    public UserBuilder withLogin(String login) {
        this.login = login;
        return this;
    }

    /**
     * Sets first name according to given parameter.
     *
     * @param firstName user's first name.
     * @return current builder.
     */
    public UserBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    /**
     * Sets last name according to given parameter.
     *
     * @param lastName user's last name.
     * @return current builder.
     */
    public UserBuilder withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    /**
     * Sets e-mail according to given parameter.
     *
     * @param email user's e-mail.
     * @return current builder.
     */
    public UserBuilder hasEmail(String email) {
        this.email = email;
        return this;
    }

    /**
     * Sets phone number according to given parameter.
     *
     * @param phoneNumber user's phone number.
     * @return current builder.
     */
    public UserBuilder hasPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }


    /**
     * Sets balance according to given parameter.
     *
     * @param balance user money balance.
     * @return current builder.
     */
    public UserBuilder withBalance(int balance) {
        this.balance = balance;
        return this;
    }

    /**
     * Sets frozen money according to given parameter.
     *
     * @param frozenMoney user frozen money when he make a bid.
     * @return current builder.
     */
    public UserBuilder withFrozenMoney(int frozenMoney) {
        this.frozenMoney = frozenMoney;
        return this;
    }


    /**
     * Sets status of user( according to given parameter.
     *
     * @param isBlocked user is blocked(0) or not(1).
     * @return current builder.
     */
    public UserBuilder isBlocked(int isBlocked) {
        this.isBlocked = isBlocked;
        return this;
    }

    /**
     * Builds and returns {@link User} object in accordance with set earlier
     * parameters (userId, role, login, first name, last name, e-mail, phoneNumber, balance, frozenMoney
     * and blocked).
     *
     * @return object with information about new user.
     */
    public User build() {
        User user = new User();
        user.setId(userId);
        user.setRole(role);
        user.setLogin(login);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setBalance(balance);
        user.setFrozenMoney(frozenMoney);
        user.setIsBlocked(isBlocked);
        return user;
    }
}
