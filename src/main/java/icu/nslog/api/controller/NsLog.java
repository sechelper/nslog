package icu.nslog.api.controller;

import icu.nslog.api.bean.DnsRecord;
import icu.nslog.api.mapper.DnsLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class NsLog {

    @Autowired
    public DnsLogMapper dnsLogMapper;

    @GetMapping("/dnslog/{domain}")
    public int isAlive(@PathVariable String domain, int start, int end){
        System.out.println("domain = " + domain + ", start = " + start + ", end = " + end);
        // user domain
        List<Map<Object, Object>> records = dnsLogMapper.qryRecordByDomain(domain);
        if (records != null) return records.size();
        return 0;
    }

    @PostMapping("/record")
    public int record(DnsRecord record){
        return 0;
    }
}
