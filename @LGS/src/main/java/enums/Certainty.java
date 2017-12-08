package enums;

/**
 * Created by Dennis van Opstal on 8-12-2017.
 */
public enum Certainty {
    PROBABLY("Probably"),
    MAYBE("Maybe"),
    PROBABLY_NOT("Probably Not");

    private String certainty;

    Certainty(String certainty) {
        this.certainty = certainty;
    }

    public String getCertainty() {
        return certainty;
    }
}
