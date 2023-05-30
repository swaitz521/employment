package com.employment.employments.controller;

import com.employment.employments.controller.ex.FileEmptyException;
import com.employment.employments.controller.ex.FileSizeException;
import com.employment.employments.controller.ex.FileTypeException;
import com.employment.employments.service.ex.PasswordNotMatchException;
import com.employment.employments.service.ex.ServiceException;
import com.employment.employments.service.ex.UserNotFoundException;
import com.employment.employments.service.ex.UsernameDuplicateException;
import com.employment.employments.util.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class BaseController {
    //用于统一处理方法抛出的异常
    @ExceptionHandler(ServiceException.class)
    public Result handleException(Throwable e){
        Result result=new Result<>(e);
        if(e instanceof UserNotFoundException){
            result.setCode(400);
        } else if(e instanceof PasswordNotMatchException){
            result.setCode(500);
        }else if(e instanceof UsernameDuplicateException){
            result.setCode(401);
        }else if(e instanceof FileEmptyException){
            result.setCode(6000);
        }else if(e instanceof FileSizeException){
            result.setCode(6001);
        }else if(e instanceof FileTypeException){
            result.setCode(6002);
        }
        return result;
    }
}
