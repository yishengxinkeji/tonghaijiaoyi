package com.ruoyi.framework.web.exception.frontExceptionHandler;

import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.base.ResponseResult;
import com.ruoyi.common.exception.frontException.VipUserException;
import com.ruoyi.framework.util.PermissionUtils;
import com.ruoyi.framework.web.exception.DefaultExceptionHandler;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class VipUserExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(DefaultExceptionHandler.class);

    public static ResponseResult result = new ResponseResult();

    /**
     * 操作失败
     */
    @ExceptionHandler(VipUserException.class)
    public ResponseResult handleVipUserException(VipUserException e) {
        log.error(e.getMessage(), e);
        return result.error();
    }

}
