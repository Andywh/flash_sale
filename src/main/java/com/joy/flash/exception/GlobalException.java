package com.joy.flash.exception;

import com.joy.flash.enums.ResultEnum;
import lombok.Getter;

/**
 * Created by SongLiang on 2019-09-10
 */

@Getter
public class GlobalException extends RuntimeException {

    private ResultEnum resultEnum;

    public GlobalException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.resultEnum = resultEnum;
    }

}
