package icu.nslog.config;

import icu.nslog.NslogStatus;
import icu.nslog.api.bean.NslogResultBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @className: SimpleAccessDeniedHandler
 * @description: TODO
 * @author: cookun
 * @date: 1/12/22
 **/
public class FormLoginAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();
        writer.println(NslogResultBean.failed(NslogStatus.UNAUTHORIZED, "You do not have permission to access"));
        writer.flush();
        writer.close();
    }
}
