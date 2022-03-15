package icu.nslog.config;

import com.sun.net.httpserver.Authenticator;
import icu.nslog.NslogStatus;
import icu.nslog.api.bean.NslogResultBean;
import icu.nslog.exception.NslogException;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;

/**
 * @className: ResponseBodyAdivice
 * @description: TODO
 * @author: cookun
 * @date: 1/7/22
 **/
@RestControllerAdvice
public class ResponseResultBodyAdvice implements ResponseBodyAdvice<Object> {
    private static final Class<? extends Annotation> ANNOTATION_TYPE = ResponseResultBody.class;

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return AnnotatedElementUtils.hasAnnotation(methodParameter.getContainingClass(), ANNOTATION_TYPE)|| methodParameter.hasMethodAnnotation(ANNOTATION_TYPE);
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (o instanceof NslogResultBean){
            return o;
        }
        return NslogResultBean.success(o);
    }

    @ExceptionHandler(Exception.class)
    public NslogResultBean<String> handleAllExceptionMethod(NslogException e, WebRequest request, HttpServletResponse response) {
        return NslogResultBean.failed(e.getStatus(), e.getMessage());
    }
}
