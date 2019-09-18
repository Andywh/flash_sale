package com.joy.flash.utils;

import com.joy.flash.VO.ResultVO;
import com.joy.flash.enums.ResultEnum;

/**
 * Created by SongLiang on 2019-09-09
 */
public class ResultVOUtil {

    public static ResultVO success() {
        return success(null);
    }

    public static ResultVO success(Object obj) {
        ResultVO result = new ResultVO();
        result.setCode(0);
        result.setMsg("成功");
        result.setData(obj);
        return result;
    }

    public static ResultVO error(ResultEnum resultEnum) {
        ResultVO result = new ResultVO();
        result.setCode(resultEnum.getCode());
        result.setMsg(resultEnum.getMessage());
        return result;

    }

    public static ResultVO error(Integer code, String msg) {
        ResultVO result = new ResultVO();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

}
