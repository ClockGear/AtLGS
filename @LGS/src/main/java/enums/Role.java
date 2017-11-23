package enums;

/**
 * Created by Dennis van Opstal on 23-11-2017.
 */
public enum Role {
    NORMAL_USER("Normal User"),
    LGS("LGS"),
    ADMIN("Admin");

    private String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}