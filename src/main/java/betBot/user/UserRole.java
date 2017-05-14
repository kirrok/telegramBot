package betBot.user;

public enum UserRole {


    ADMIN("АДМИН"), USER("ПОЛЬЗОВАТЕЛЬ"), NOT_AUTHORIZED("НЕ АВТОРИЗОВАН");

    String value;

    UserRole(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}