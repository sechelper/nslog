package icu.nslog.api.controller;

import icu.nslog.NslogStatus;
import icu.nslog.api.bean.DnsRecord;
import icu.nslog.api.bean.NslogResultBean;
import icu.nslog.api.bean.OpenApiAuthorizationDetails;
import icu.nslog.api.mapper.AuthorizationMapper;
import icu.nslog.config.ResponseResultBody;
import icu.nslog.exception.NslogException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @className: OpenAapi
 * @description: TODO
 * @author: cookun
 * @date: 1/5/22
 **/
@RestController
@RequestMapping("/openapi")
@ResponseResultBody
public class OpenApi {

    @Autowired
    private AuthorizationMapper authorizationMapper;

    @PostMapping("/accessToken")
    public String accessToken(String appId, String secret) {
//        System.out.println(accessTokenMapper);
//        UserAuthorizationDetails user = authorizationMapper.qryUserEntityByUsername(appId);
//        if (user.getSecret().equals(secret)){
//            return accessTokenMapper.allocationToken(user);
//        }
//        //TODO throw new AuthenticationFailed()
//        throw new RuntimeException("Authorization error.");
        return null;
    }

    @GetMapping("/accessToken")
    public NslogResultBean<OpenApiAuthorizationDetails> accessToken2(String appId, String secret) {
        OpenApiAuthorizationDetails details = authorizationMapper.qryBasicAuthorizationByAppId(appId);
        if (details == null) {
            throw new NslogException(NslogStatus.NOT_FOUND, "test");
        }
        return NslogResultBean.success(details);
    }


    @PostMapping("/arching")
    public void arching(@RequestBody DnsRecord record) {
        System.out.println(record.toString());
    }

}
