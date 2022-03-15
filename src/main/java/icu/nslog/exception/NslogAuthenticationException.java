package icu.nslog.exception;

import icu.nslog.NslogStatus;

/**
 * @className: NslogAuthenticationException
 * @description: TODO
 * @author: cookun
 * @date: 1/12/22
 **/
public class NslogAuthenticationException extends NslogException{

    public NslogAuthenticationException(NslogStatus status, String message) {
        super(status, message);
    }
}
