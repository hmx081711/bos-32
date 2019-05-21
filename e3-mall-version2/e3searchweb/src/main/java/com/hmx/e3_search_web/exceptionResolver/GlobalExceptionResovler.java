package com.hmx.e3_search_web.exceptionResolver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GlobalExceptionResovler implements HandlerExceptionResolver {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionResovler.class);
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        //在控制台打印异常信息
        ex.printStackTrace();
        //写日志
        logger.error("系统出现错误",ex);
        logger.info("系统出现问题");
        //发邮件
        //显示错误界面
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error/exception");
        modelAndView.addObject("message","系统发生异常请稍后重试");
        return modelAndView;
    }
}
