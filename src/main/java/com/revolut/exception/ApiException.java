package com.revolut.exception;

import com.revolut.util.JsonUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.Map;


public class ApiException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String code;
    private String description;
    private Integer statusCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
    ;

    public ApiException(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public ApiException(String code, String description, Integer statusCode) {
        this.code = code;
        this.description = description;
        this.statusCode = statusCode;
    }

    public ApiException(String code, String description, Integer statusCode, Throwable cause) {
        super(cause);
        this.code = code;
        this.description = description;
        this.statusCode = statusCode;
    }

    public ApiException(String code, String description, Throwable cause) {
        super(cause);
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatusCode() {
        return this.statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String toJson() {
        Map<String, Object> exceptionMap = new LinkedHashMap<>();

        exceptionMap.put("error", this.code);
        exceptionMap.put("message", this.description);
        exceptionMap.put("status", this.statusCode);

//        try {
//            return JsonUtils.toJson(exceptionMap);
//        } catch (Exception exception) {
        return "{" +
                "\"error\": " +
                "\"" + this.code + "\", " +
                "\"message\": " +
                "\"" + this.description + "\", " +
                "\"status\": " +
                this.statusCode +
                "}";
//        }
    }

}
