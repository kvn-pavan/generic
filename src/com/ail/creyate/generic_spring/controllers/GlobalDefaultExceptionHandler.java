package com.ail.creyate.generic_spring.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ail.creyate.generic_spring.utils.Constants;

@ControllerAdvice
class GlobalDefaultExceptionHandler {
    public static final String DEFAULT_ERROR_VIEW = "error";

    @ExceptionHandler(value = Exception.class)
    public String defaultErrorHandler(HttpServletRequest request, Exception e) throws Exception {
        // If the exception is annotated with @ResponseStatus rethrow it and let
        // the framework handle it - like the OrderNotFoundException example
        // at the start of this post.
        // AnnotationUtils is a Spring Framework utility class.
        /*if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null)
            throw e;*/

    	request.setAttribute("errorMsg", e.getMessage());
    	request.setAttribute("errorCause", e);
        // Otherwise setup and send the user to a default error-view.
        return Constants.ERROR_VIEW;
    }
}
