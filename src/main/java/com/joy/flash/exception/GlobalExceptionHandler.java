package com.joy.flash.exception;

import com.joy.flash.VO.ResultVO;
import com.joy.flash.enums.ResultEnum;
import com.joy.flash.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by SongLiang on 2019-09-10
 */
@Slf4j
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResultVO exceptionHandler(HttpServletRequest request, Exception e) {
        if (e instanceof GlobalException) {
            GlobalException ex = (GlobalException)e;
            log.info("GlobalException: {}", ex.getMessage());
            return ResultVOUtil.error(ex.getResultEnum());
        } else if (e instanceof BindException) {
            BindException ex = (BindException)e;
            List<ObjectError> errors = ex.getAllErrors();
            ObjectError error = errors.get(0);
            String msg = error.getDefaultMessage();
            return ResultVOUtil.error(ResultEnum.BIND_ERROR.getCode(), fillArgs(msg));
        } else {
            return ResultVOUtil.error(ResultEnum.SERVER_ERROR);
        }
    }

    public String fillArgs(Object... args) {
        String message = String.format(ResultEnum.BIND_ERROR.getMessage(), args);
        return message;
    }

}
