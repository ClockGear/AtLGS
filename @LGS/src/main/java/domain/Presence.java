package domain;

import enums.Certainty;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Dennis van Opstal on 10-11-2017.
 */
@Entity
@NamedQueries(
        @NamedQuery(name = "getAllPresences", query = "select p from Presence p")
)
public class Presence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Date date;
    private Date startTime;
    private Date endTime;
    private Certainty certainty;

    @ManyToOne
    private User user;

    @ManyToOne
    private LGS lgs;

    @ManyToOne
    private Format format;

    private static final SimpleDateFormat SDF = new SimpleDateFormat("HH:mm");
    private static final SimpleDateFormat SDF2 = new SimpleDateFormat("dd-MM-yyyy");

    public Presence(long id, Date startTime, Date date, Date endTime, Certainty certainty, User user, LGS lgs, Format format) {
        this.id = id;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.certainty = certainty;
        this.user = user;
        this.lgs = lgs;
        this.format = format;
    }

    public Presence(long id, String dateString, String startTimeString, String endTimeString, Certainty certainty, User user, LGS lgs, Format format) {
        this.id = id;
        try {
            this.date = SDF2.parse(dateString);
            this.startTime = SDF.parse(startTimeString);
            this.endTime = SDF.parse(endTimeString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.certainty = certainty;
        this.user = user;
        this.lgs = lgs;
        this.format = format;
    }

    public Presence(Date date, Date startTime, Date endTime, Certainty certainty, User user, LGS lgs, Format format) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.certainty = certainty;
        this.user = user;
        this.lgs = lgs;
        this.format = format;
    }

    public Presence(String dateString, String startTimeString, String endTimeString, Certainty certainty, User user, LGS lgs, Format format) {
        try {
            this.date = SDF2.parse(dateString);
            this.startTime = SDF.parse(startTimeString);
            this.endTime = SDF.parse(endTimeString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.certainty = certainty;
        this.user = user;
        this.lgs = lgs;
        this.format = format;
    }

    public Presence() {

    }

    public long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public String getDateString() {
        return SDF2.format(date);
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

    public Certainty getCertainty() {
        return certainty;
    }

    public User getUser() {
        return user;
    }

    public LGS getLgs() {
        return lgs;
    }

    public Format getFormat() {
        return format;
    }

    public static SimpleDateFormat getSDF() {
        return SDF;
    }
}
