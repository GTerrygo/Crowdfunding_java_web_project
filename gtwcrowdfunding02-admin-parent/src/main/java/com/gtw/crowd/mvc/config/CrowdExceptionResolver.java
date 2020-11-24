package com.gtw.crowd.mvc.config;

import com.google.gson.Gson;

import com.gtw.crowd.util.exception.LoginAcctAlreadyUseException;
import com.gtw.crowd.util.exception.LoginFailedException;
import com.gtw.crowd.util.util.CrowdUtil;
import com.gtw.crowd.util.util.ResultEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author
 * @create 2020-10-26-1:34
 */
@ControllerAdvice
public class CrowdExceptionResolver {


    /**
     * This function is to process the exception when register a new admin Account but the name has already exist
     * @param exception
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value= LoginAcctAlreadyUseException.class)
    public ModelAndView resolveLoginAcctAlreadyUseExceptionException(LoginAcctAlreadyUseException exception,
                                                       HttpServletRequest request,
                                                       HttpServletResponse response) throws Exception {
        String viewName= "admin-add";
        return commonExceptionResolver(exception,viewName,request,response);
    }


    /**
     * This function is to process the exception when login has errors such as password is not right
     * @param exception
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value= Exception.class)
    public ModelAndView resolveException(Exception exception,
                                                    HttpServletRequest request,
                                                    HttpServletResponse response) throws Exception {
        String viewName= "admin-login";
        return commonExceptionResolver(exception,viewName,request,response);
    }
    /**
     * This function is to process the exception when login has errors such as password is not right
     * @param exception
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value= LoginFailedException.class)
    public ModelAndView resolveLoginFailedException(LoginFailedException exception,
                                                    HttpServletRequest request,
                                                    HttpServletResponse response) throws Exception {
        String viewName= "admin-login";
        return commonExceptionResolver(exception,viewName,request,response);
    }

    /**
     * this function is for nullPointException
     * @param exception
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @ExceptionHandler(value=NullPointerException.class)
    public ModelAndView resolveNULLPointException(NullPointerException exception,
                                                  HttpServletRequest request,
                                                  HttpServletResponse response) throws IOException {
        String viewName="system-error";
        return commonExceptionResolver(exception,viewName,request,response);
    }


    /**
     * This function is to determine the request is json or html. if the request is html, return html file. if the request
     * is json, return json object.
     * @param exception
     * @param viewName
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    private ModelAndView commonExceptionResolver(Exception exception,String viewName,HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean b = CrowdUtil.judgeRequstType(request);
        if(b) {
            ResultEntity resultEntity=ResultEntity.error();
            resultEntity.setMessage(exception.getMessage());
            Gson gson=new Gson();
            String json = gson.toJson(resultEntity);
            response.getWriter().write(json);
            return null;
        }else{
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("exception",exception);
            modelAndView.setViewName(viewName);
            return modelAndView;
        }
    }

}
