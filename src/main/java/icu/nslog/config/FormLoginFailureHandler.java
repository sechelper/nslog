package icu.nslog.config;

import icu.nslog.NslogStatus;
import icu.nslog.api.bean.NslogResultBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @className: FormLoginFailureHandler
 * @description: TODO
 * @author: cookun
 * @date: 1/12/22
 **/
public class FormLoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();
        writer.println(NslogResultBean.failed(NslogStatus.UNAUTHORIZED, exception.getMessage()));
        writer.flush();
        writer.close();
    }
}
