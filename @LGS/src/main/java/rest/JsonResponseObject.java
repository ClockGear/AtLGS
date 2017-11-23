package rest;

/**
 * Created by Dennis van Opstal on 23-11-2017.
 */
public class JsonResponseObject {
    private boolean error;
    private Object object;
    private String message;

    public JsonResponseObject(boolean error, Object object) {
        this.error = error;
        this.object = object;
    }

    public JsonResponseObject(boolean error, String message) {
        this.error = error;
        this.message = message;
    }

    public JsonResponseObject(boolean error, Object object, String message) {
        this.error = error;
        this.object = object;
        this.message = message;
    }

    public boolean isError() {
        return error;
    }

    public Object getObject() {
        return object;
    }

    public String getMessage() {
        return message;
    }
}
