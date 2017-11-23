package domain;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Dennis van Opstal on 10-11-2017.
 */
@Entity
@NamedQueries(
        @NamedQuery(name = "getAllGameEvents", query = "select ge from GameEvent ge")
)
public class GameEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    private Date startTime;
    private Date endTime;
    private double price;

    @ManyToOne
    private Format format;

    @ManyToOne
    private LGS lgs;

    private static final SimpleDateFormat SDF = new SimpleDateFormat("hh:mm");

    public GameEvent(long id, String name, String description, Date startTime, Date endTime, double price, Format format, LGS lgs) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
        this.format = format;
        this.lgs = lgs;
    }

    public GameEvent(long id, String name, String description, String startTimeString, String endTimeString, double price, Format format, LGS lgs) {
        this.id = id;
        this.name = name;
        this.description = description;
        try {
            this.startTime = SDF.parse(startTimeString);
            this.endTime = SDF.parse(endTimeString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.price = price;
        this.format = format;
        this.lgs = lgs;
    }

    public GameEvent(String name, String description, Date startTime, Date endTime, double price, Format format, LGS lgs) {
        this.name = name;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
        this.format = format;
        this.lgs = lgs;
    }

    public GameEvent(String name, String description, String startTimeString, String endTimeString, double price, Format format, LGS lgs) {
        this.name = name;
        this.description = description;
        try {
            this.startTime = SDF.parse(startTimeString);
            this.endTime = SDF.parse(endTimeString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.price = price;
        this.format = format;
        this.lgs = lgs;
    }

    public GameEvent() {

    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Date getStartTime() {
        return startTime;
    }

    public String getStartTimeString() {
        return SDF.format(startTime);
    }

    public Date getEndTime() {
        return endTime;
    }

    public String getEndTimeString() {
        return SDF.format(endTime);
    }

    public double getPrice() {
        return price;
    }

    public Format getFormat() {
        return format;
    }

    public LGS getLgs() {
        return lgs;
    }
}
