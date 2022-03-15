package icu.nslog.config;

import icu.nslog.NslogStatus;
import icu.nslog.api.bean.UserAuthorizationDetails;
import icu.nslog.api.mapper.AuthorizationMapper;
import icu.nslog.exception.NslogAuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public class FormLoginAuthenticationDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AuthorizationMapper authorizationMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAuthorizationDetails user = authorizationMapper.qryUserEntityByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("Invalid username");
        }
        user.setAuthorities(authorizationMapper.qryUserRolesByUserId(user.getId()));
        return user;
    }
}
