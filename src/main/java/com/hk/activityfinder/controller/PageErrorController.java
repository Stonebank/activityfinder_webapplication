package com.hk.activityfinder.controller;

import com.hk.activityfinder.service.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;


@Controller
public class PageErrorController implements ErrorController {
    private final Logger logger = LoggerFactory.getLogger(PageErrorController.class);

    @Autowired
    private Email email;

    private final String[] IP_HEADER_CANDIDATES = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR"
    };


    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        var status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            var responseCode = Integer.parseInt(status.toString());
            email.sendMail("hassan_99@live.dk", "User encountered error with response code " + responseCode, "Error " + responseCode);
            logger.error("ERROR! A user went too far - response code: " + responseCode + " " + getClientIP());
            if (responseCode == HttpStatus.NOT_FOUND.value())
                return "error-404";
            if (responseCode == HttpStatus.INTERNAL_SERVER_ERROR.value())
                return "error-500";
        }
        email.sendMail("hassan_99@live.dk", "User encountered error! Unknown response code", "Error: " + request.getServletPath() + " " + getClientIP());
        logger.error("Error detected - normal request: /error");
        return "error";
    }

    private String getClientIP() {
        if (RequestContextHolder.getRequestAttributes() == null) {
            return "0.0.0.0";
        }
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        for (String header : IP_HEADER_CANDIDATES) {
            var ip_list = request.getHeader(header);
            if (ip_list != null && ip_list.length() != 0 && !"unknown".equalsIgnoreCase(ip_list))
                return ip_list.split(",")[0];
        }
        return request.getRemoteAddr();
    }

}
