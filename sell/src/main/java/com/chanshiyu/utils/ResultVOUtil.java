package com.chanshiyu.utils;

import com.chanshiyu.VO.ResultVO;
import com.chanshiyu.dto.PageDTO;
import org.springframework.data.domain.Page;

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

    public static ResultVO successPage(Page page) {
        PageDTO pageDTO = new PageDTO();
        pageDTO.setPageNum(page.getPageable().getPageNumber() + 1);
        pageDTO.setPageSize(page.getSize());
        pageDTO.setTotal(page.getTotalElements());

        ResultVO resultVO = success(page.getContent());
        resultVO.setAttributes(pageDTO);
        return resultVO;
    }

    public static ResultVO error(Integer status, String msg) {
        ResultVO resultVO = new ResultVO();
        resultVO.setStatus(status);
        resultVO.setMsg(msg);
        return resultVO;
    }
}
