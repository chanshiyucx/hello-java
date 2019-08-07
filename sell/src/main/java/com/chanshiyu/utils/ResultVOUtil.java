package com.chanshiyu.utils;

import com.chanshiyu.VO.ResultVO;
import com.chanshiyu.VO.PageVO;
import org.springframework.data.domain.Page;

public class ResultVOUtil {

    public static ResultVO success(Object object) {
        ResultVO resultVO = new ResultVO();
        resultVO.setStatus(200);
        resultVO.setMsg("OK");
        resultVO.setData(object);
        return resultVO;
    }

    public static ResultVO success() {
        return success(null);
    }

    public static ResultVO successPage(Page page) {
        PageVO pageVO = new PageVO();
        pageVO.setPageNum(page.getPageable().getPageNumber() + 1);
        pageVO.setPageSize(page.getSize());
        pageVO.setTotal(page.getTotalElements());

        ResultVO resultVO = success(page.getContent());
        resultVO.setAttributes(pageVO);
        return resultVO;
    }

    public static ResultVO error(Integer status, String msg) {
        ResultVO resultVO = new ResultVO();
        resultVO.setStatus(status);
        resultVO.setMsg(msg);
        return resultVO;
    }
}
