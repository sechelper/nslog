package icu.nslog.api.bean;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;


public class DnsRecord {
    private long id;
    public String domain;
    public String source;
    public Integer userId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    public Timestamp timestamp;

    public DnsRecord(String domain, String source) {
        this.domain = domain;
        this.source = source;
    }

    public DnsRecord(String domain, String source, Integer userId) {
        this.domain = domain;
        this.source = source;
        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "DnsRecord{" +
                "id=" + id +
                ", domain='" + domain + '\'' +
                ", source='" + source + '\'' +
                ", userId=" + userId +
                ", timestamp=" + timestamp +
                '}';
    }
}
