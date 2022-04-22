package com.bot.message.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
public class MessageControllerAdvice {

    @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView handleError404(HttpServletRequest request, Exception e) {
        Log.info("Request: {} not found", request.getRequestURL());
        return new ModelAndView("404");
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleError(HttpServletRequest request, Exception e) {
        Log.error("Request: {} raised", request.getRequestURL(), e);
        return new ModelAndView("error");
    }

}