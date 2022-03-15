package icu.nslog.config;

import icu.nslog.api.bean.OpenApiAuthorizationDetails;
import icu.nslog.api.mapper.AuthorizationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

/**
 * @className: OpenApiUserDetailsServiceImpl
 * @description: TODO
 * @author: cookun
 * @date: 1/5/22
 **/
public class OpenApiAuthenticationDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AuthorizationMapper authorizationMapper;

    @Override
    public UserDetails loadUserByUsername(String appId) throws UsernameNotFoundException {
        OpenApiAuthorizationDetails details = authorizationMapper.qryBasicAuthorizationByAppId(appId);
        details.setAuthorities(authorizationMapper.qryOpenApiRolesByAccessId(details.getId()));
        return details;
    }

}
