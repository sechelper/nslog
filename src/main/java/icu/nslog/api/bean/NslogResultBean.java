package icu.nslog.api.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import icu.nslog.NslogStatus;
import icu.nslog.exception.NslogException;
import org.springframework.http.HttpStatus;


import java.util.HashMap;

/**
 * @className: NslogResultBean
 * @description: TODO
 * @author: cookun
 * @date: 1/7/22
 **/
//https://blog.csdn.net/qq_34347620/article/details/102239179

@JsonInclude(JsonInclude.Include.NON_NULL)
public class NslogResultBean<T> {

    private Integer code;
    private String status;
    private T result;
    private String error;

    public NslogResultBean(NslogStatus status) {
        this.code = status.getCode();
        this.status = status.getStatus();
    }

    public NslogResultBean(NslogStatus status, T result) {
        this.code = status.getCode();
        this.status = status.getStatus();
        this.result = result;
    }

    public NslogResultBean(NslogStatus status, String error) {
        this.code = status.getCode();
        this.status = status.getStatus();
        this.error = error;
    }

    public NslogResultBean(Integer code, String status, T result) {
        this.code = code;
        this.status = status;
        this.result = result;
    }

    public NslogResultBean(Integer code, String status, String error) {
        this.code = code;
        this.status = status;
        this.error = error;
    }

    public static NslogResultBean<Object> success(){
        return new NslogResultBean<Object>(NslogStatus.SUCCESS, new HashMap<>());
    }

    public static <T> NslogResultBean<T> success(T result){
        return new NslogResultBean<T>(NslogStatus.SUCCESS, result);
    }

    public static <T> NslogResultBean<T> success(NslogStatus status, T result){
        return new NslogResultBean<T>(status, result);
    }

    public static <T> NslogResultBean<T> success(Integer code, String status, T result){
        return new NslogResultBean<T>(code, status, result);
    }

    public static <T> NslogResultBean<T> failed(NslogStatus status, String error){
        return new NslogResultBean<T>(status, error);
    }

    public static <T> NslogResultBean<T> failed(Integer code, String status, String error){
        return new NslogResultBean<T>(code, status, error);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        ObjectMapper o = new ObjectMapper();
        try {
            return o.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new NslogException(NslogStatus.INTERNAL_ERROR, "Invalid result.");
        }
    }
}