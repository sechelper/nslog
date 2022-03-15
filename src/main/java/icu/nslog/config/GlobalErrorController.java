package icu.nslog.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import icu.nslog.api.bean.NslogResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @className: MyErrorController
 * @description: TODO
 * @author: cookun
 * @date: 1/11/22
 **/
@Controller
public class GlobalErrorController extends BasicErrorController {
    @Autowired
    public GlobalErrorController(ErrorAttributes errorAttributes,
                                 ServerProperties serverProperties,
                                 List<ErrorViewResolver> errorViewResolvers) {
        super(errorAttributes, serverProperties.getError(), errorViewResolvers);
    }

    @RequestMapping
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        HttpStatus status = getStatus(request);
        if (status == HttpStatus.NO_CONTENT) {
            return new ResponseEntity<>(status);
        }
        ErrorAttributeOptions options = ErrorAttributeOptions.defaults();
        Map<String, Object> body = getErrorAttributes(request,  options.including(ErrorAttributeOptions.Include.MESSAGE));
        ObjectMapper mapper = new ObjectMapper();
        NslogResultBean nslogResultBean = NslogResultBean.failed(status.value(), status.getReasonPhrase(), body.get("message").toString());
        return new ResponseEntity<Map<String, Object>>(mapper.convertValue(nslogResultBean, Map.class), status);
    }
}
