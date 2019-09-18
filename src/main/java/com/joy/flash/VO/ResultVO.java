package com.joy.flash.VO;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by SongLiang on 2019-09-09
 */
@Data
public class ResultVO<T> implements Serializable {

    private Integer code;

    private String msg;

    private T data;

}
