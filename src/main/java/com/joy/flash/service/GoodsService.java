package com.joy.flash.service;

import com.joy.flash.VO.GoodsVO;
import com.joy.flash.dao.GoodsMapper;
import com.joy.flash.model.Goods;
import com.joy.flash.model.MiaoshaGoods;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by SongLiang on 2019-09-11
 */
@Service
@Slf4j
public class GoodsService {

    @Autowired
    GoodsMapper goodsMapper;

    public List<GoodsVO> getGoodsVOList() {
        return goodsMapper.getGoodsVOList();
    }

    public GoodsVO getGoodsVOByGoodsId(Long goodId) {
        return goodsMapper.getGoodsVOByGoodsId(goodId);
    }

    public boolean reduceStock(GoodsVO goods) {
        MiaoshaGoods g = new MiaoshaGoods();
        g.setGoodsId(goods.getId());
        g.setStockCount(goods.getStockCount()-1);
        int ret = goodsMapper.reduceStock(g);
        return ret > 0;
    }
}
