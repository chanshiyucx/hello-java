package com.chanshiyu.utils;

import com.chanshiyu.VO.ResultVO;

public class ResultVOUtil {

    public static ResultVO success(Object object) {
        ResultVO resultVO = new ResultVO();
        resultVO.setStatus(200);
        resultVO.setMsg("ok");
        resultVO.setData(object);
        return resultVO;
    }

    public static ResultVO success() {
        return success(null);
    }

    public static ResultVO error(Integer code, String msg) {
        ResultVO resultVO = new ResultVO();
        resultVO.setStatus(code);
        resultVO.setMsg(msg);
        return resultVO;
    }
}