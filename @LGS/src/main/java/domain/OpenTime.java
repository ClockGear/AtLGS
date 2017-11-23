package domain;

import enums.WeekDay;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Dennis van Opstal on 10-11-2017.
 */
@Entity
@NamedQueries(
        @NamedQuery(name = "getAllOpenTimes", query = "select ot from OpenTime ot")
)
public class OpenTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private WeekDay day;
    private Date openTime;
    private Date closeTime;

    @ManyToOne
    private LGS lgs;

    private static final SimpleDateFormat SDF = new SimpleDateFormat("hh:mm");

    public OpenTime(long id, WeekDay day, Date openTime, Date closeTime, LGS lgs) {
        this.id = id;
        this.day = day;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.lgs = lgs;
    }

    public OpenTime(long id, WeekDay day, String openTimeString, String closeTimeString, LGS lgs) {
        this.id = id;
        this.day = day;
        try {
            this.openTime = SDF.parse(openTimeString);
            this.closeTime = SDF.parse(closeTimeString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.lgs = lgs;
    }

    public OpenTime(WeekDay day, Date openTime, Date closeTime, LGS lgs) {
        this.day = day;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.lgs = lgs;
    }

    public OpenTime(WeekDay day, String openTimeString, String closeTimeString, LGS lgs) {
        this.day = day;
        try {
            this.openTime = SDF.parse(openTimeString);
            this.closeTime = SDF.parse(closeTimeString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.lgs = lgs;
    }

    public OpenTime() {

    }

    public long getId() {
        return id;
    }

    public WeekDay getDay() {
        return day;
    }

    public Date getOpenTime() {
        return openTime;
    }

    public String getOpenTimeString() {
        return SDF.format(openTime);
    }

    public Date getCloseTime() {
        return closeTime;
    }

    public String getCloseTimeString() {
        return SDF.format(closeTime);
    }

    public LGS getLgs() {
        return lgs;
    }
}
