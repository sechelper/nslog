package icu.nslog;

public enum NslogStatus {

    SUCCESS(20000,"OK"),
    CREATED(20100,"Created"),
    BAD_REQUEST(40000,"Bad Request"),
    UNAUTHORIZED(40100,"Unauthorized"),
    NOT_FOUND(40400,"Not found"),
    UNPROCESSABLE_ENTITY(42200,"Unprocessable Entity"),
    INTERNAL_ERROR(50000,"Internal Error");

    private int code;
    private String status;

    NslogStatus(int code, String status) {
        this.code = code;
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
