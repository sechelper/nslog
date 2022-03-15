package icu.nslog.config;

import org.springframework.security.core.GrantedAuthority;

/**
 * @className: UserGrantedAuthorityImpl
 * @description: TODO
 * @author: cookun
 * @date: 1/5/22
 **/
public class UserGrantedAuthorityImpl implements GrantedAuthority {

    public static final String Authority_PREFIX = "ROLE_";

    private final String role;

    public UserGrantedAuthorityImpl(String role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return Authority_PREFIX + this.role;
    }
}
