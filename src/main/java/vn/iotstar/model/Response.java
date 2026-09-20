package vn.iotstar.model;

public class Response {
    private boolean status;
    private String message;
    private Object body;

    public Response() {
    }

    public Response(boolean status, String message, Object body) {
        this.status = status;
        this.message = message;
        this.body = body;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}