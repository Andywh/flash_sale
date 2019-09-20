package com.joy.flash.rabbitmq;

import com.joy.flash.model.MiaoshaOrder;
import com.joy.flash.model.MiaoshaUser;
import lombok.Data;

/**
 * Created by SongLiang on 2019-09-19
 */
@Data
public class MiaoshaMessage {

    private MiaoshaUser user;

    private long goodsId;

}
