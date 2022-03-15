package icu.nslog.exception;

import icu.nslog.NslogStatus;

public class NslogException extends RuntimeException{
    private NslogStatus status;

    public NslogException(NslogStatus status, String message) {
        super(message);
        this.status = status;
    }

    public NslogStatus getStatus() {
        return status;
    }

    public void setStatus(NslogStatus status) {
        this.status = status;
    }
}
