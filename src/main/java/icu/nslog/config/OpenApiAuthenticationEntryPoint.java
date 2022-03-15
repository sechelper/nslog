package icu.nslog.config;

import icu.nslog.NslogStatus;
import icu.nslog.api.bean.NslogResultBean;
import icu.nslog.exception.NslogException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @className: MyBasicAuthenticationEntryPoint
 * @description: TODO
 * @author: cookun
 * @date: 1/7/22
 **/
public class OpenApiAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest request, HttpServletResponse response, AuthenticationException authEx)
            throws IOException, NslogException{
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();
        writer.println(NslogResultBean.failed(NslogStatus.UNAUTHORIZED, "You must authenticate with a valid API ID and secret.").toString());
        writer.flush();
        writer.close();
    }

    @Override
    public void afterPropertiesSet() {
        setRealmName("OpenApiAuthenticationEntryPoint");
        super.afterPropertiesSet();
    }
}