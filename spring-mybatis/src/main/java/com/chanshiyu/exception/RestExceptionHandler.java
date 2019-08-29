package com.chanshiyu.exception;

import com.chanshiyu.util.CommJSONResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.stream.Stream;

/**
 * @author chanshiyu
 * @date 2019/8/29 9:51
 * @description 全局异常提示
 */
@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommJSONResult<String> defaultErrorHandler(Exception e) {
        e.printStackTrace();
        return CommJSONResult.errorMsg(e.getMessage());
    }

    /**
     * 自定义异常
     * @param e 具体异常
     * @return 包括的类型
     */
    @ExceptionHandler(ApiException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommJSONResult<String> handleBindException(ApiException e) {
        return CommJSONResult.errorMsg(e.getMessage());
    }

    /**
     * 普通Restful接口参数判断
     *
     * @param e 具体异常
     * @return 包括的类型
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommJSONResult<String> argumentNotValidException(MethodArgumentNotValidException e) {
        return CommJSONResult.errorMsg(getValidException(e));
    }

    /**
     * webflux接口参数判断
     * @param e 具体异常
     * @return 包括的类型
     */
    @ExceptionHandler(WebExchangeBindException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommJSONResult<String> handleBindException(WebExchangeBindException e) {
        return CommJSONResult.errorMsg(getValidException(e));
    }

    /**
     * Restful 把校验异常转换为字符串
     * @param ex 具体异常
     * @return 转以后的词
     */
    private String getValidException(MethodArgumentNotValidException ex) {
        Stream<String> stream = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(e -> e.getField() + ":" + e.getDefaultMessage());
        return reduceException(stream);
    }

    /**
     * webflux 把校验异常转换为字符串
     * @param ex 具体异常
     * @return 转以后的词
     */
    private String getValidException(WebExchangeBindException ex) {
        Stream<String> stream = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(e -> e.getField() + ":" + e.getDefaultMessage());
        return reduceException(stream);
    }

    /**
     * 转换一下
     */
    private String reduceException(Stream<String> stream) {
        return stream.reduce("", (total, item) -> {
            if (StringUtils.isNotEmpty(total)) {
                total = total + "\n";
            }
            return total + item;
        });
    }
}

