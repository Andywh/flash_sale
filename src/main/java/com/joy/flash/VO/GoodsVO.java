package com.joy.flash.VO;

import com.joy.flash.model.Goods;
import lombok.Data;

import java.util.Date;

/**
 * Created by SongLiang on 2019-09-11
 */
@Data
public class GoodsVO extends Goods {

    private Double miaoshaPrice;

    private Integer stockCount;

    private Date startDate;

    private Date endDate;

}
