package com.example.crud.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


public class MyErrorController implements ErrorController {
//    private static final Logger LOGGER = LoggerFactory.getLogger(MyErrorController.class);
//    public String getErrorPath(){
//        return "/error";
//    }
//    @RequestMapping("error")
//    public String handleError(HttpServletRequest request, Model model){
//        String errorPage = "error";
//        String pageTitle = "Error";
//        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
//        if (status != null){
//            Integer statusCode = Integer.valueOf(status.toString());
//            if (statusCode == HttpStatus.NOT_FOUND.value()){
//                pageTitle="Page Not Found";
//                errorPage = "error/404";
//                LOGGER.error("Error 404");
//            }
//        }
//        model.addAttribute("pageTitle",pageTitle);
//        return errorPage;
//    }
}
