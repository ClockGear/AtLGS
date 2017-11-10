package enums;

/**
 * Created by Dennis van Opstal on 10-11-2017.
 */
public enum WeekDay {
    MONDAY("Monday"),
    TUESDAY("Tuesday"),
    WEDNESDAY("Wednesday"),
    THURSDAY("Thursday"),
    FRIDAY("Friday"),
    SATURDAY("Saturday"),
    SUNDAY("Sunday");

    private final String day;

    WeekDay(String day) {
        this.day = day;
    }

    public String getDay() {
        return day;
    }
}
