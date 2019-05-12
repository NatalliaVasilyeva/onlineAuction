package by.minsk.vasilyevanatali.auction.util.regularExpression;

public final class RegularExpressionHolder {

    public static final String EMPTY_STRING = "";
    public static final String LOGIN_REG_EX = "^[\\w]{6,40}$";
    public static final String EMAIL_REG_EX = "^([\\w-\\.]+){1,64}@([\\w&&[^_]]+){2,255}.[a-z]{2,}$";
    public static final String PASSWORD_REG_EX = "(?=^.{6,40}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$";
    public static final String MONEY_AMOUNT_REG_EX = "^\\d{0,10}(\\.\\d{1,2})?$";
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm";
    public static final String IMG_REG_EX = "[\\w]+\\.((jpg)|(png))";
    public static final String ENCODING = "encoding";
    public static final String INDEX_PATH = "INDEX_PATH";

}
