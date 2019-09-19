package com.joy.flash.VO;

import com.joy.flash.model.OrderInfo;
import lombok.Data;

/**
 * Created by SongLiang on 2019-09-18
 */
@Data
public class OrderDetailVO {

    private GoodsVO goods;

    private OrderInfo order;

}
