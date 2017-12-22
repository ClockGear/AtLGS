package enums;

/**
 * Created by Dennis van Opstal on 22-12-2017.
 */

public enum NameDisplayOption {
    FULL_NAME(0),
    FIRST_NAME(1),
    LAST_NAME(2),
    FIRST_INITIAL_LAST_NAME(3),
    FIRST_NAME_LAST_INITIAL(4),
    ANONYMOUS(5);

    private int option;

    NameDisplayOption(int option) {
        this.option = option;
    }

    public int getOption() {
        return option;
    }
}
