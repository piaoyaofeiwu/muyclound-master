package com.muyclound.provider.web;


import com.muyclound.base.enums.ErrorCodeEnum;
import com.muyclound.base.exception.BizException;
import com.muyclound.wrapper.WrapMapper;
import com.muyclound.wrapper.Wrapper;
import java.nio.file.AccessDeniedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  /**
   * 参数非法异常.
   *
   * @param e the e
   * @return the wrapper
   */
  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public Wrapper illegalArgumentException(IllegalArgumentException e) {
    log.error("参数非法异常={}", e.getMessage(), e);
    return WrapMapper.wrap(ErrorCodeEnum.GL99990400.code(), e.getMessage());
  }

  /**
   * 业务异常.
   *
   * @param e the e
   * @return the wrapper
   */
  @ExceptionHandler(BizException.class)
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public Wrapper businessException(BizException e) {
    log.error("业务异常={}", e.getMessage(), e);
    return WrapMapper.wrap(e.getCode() == 0 ? Wrapper.ERROR_CODE : e.getCode(), e.getMessage());
  }

  /**
   * 无权限访问.
   *
   * @param e the e
   * @return the wrapper
   */
  @ExceptionHandler(AccessDeniedException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  @ResponseBody
  public Wrapper unAuthorizedException(AccessDeniedException e) {
    log.error("业务异常={}", e.getMessage(), e);
    return WrapMapper.wrap(ErrorCodeEnum.GL99990401.code(), ErrorCodeEnum.GL99990401.msg());
  }


  /**
   * 全局异常.
   *
   * @param e the e
   * @return the wrapper
   */
  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ResponseBody
  public Wrapper exception(Exception e) {
    log.info("保存全局异常信息 ex={}", e.getMessage(), e);
    return WrapMapper.error();
  }
}
