package com.joy.flash.dao;

import com.joy.flash.VO.GoodsVO;
import com.joy.flash.model.Goods;
import com.joy.flash.model.MiaoshaGoods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by SongLiang on 2019-09-11
 */

@Mapper
public interface GoodsMapper {

    @Select("select a.*,b.miaosha_price as miaoshaPrice, b.stock_count as stockCount, b.start_date as startDate, b.end_date as endDate from goods as a, miaosha_goods as b where a.id = b.goods_id")
    List<GoodsVO> getGoodsVOList();

    @Select("select a.*,b.miaosha_price as miaoshaPrice, b.stock_count as stockCount, b.start_date as startDate, b.end_date as endDate from goods as a, miaosha_goods as b where a.id = b.goods_id and a.id = #{goodId}")
    GoodsVO getGoodsVOByGoodsId(Long goodId);

    @Update("update miaosha_goods set stock_count = stock_count - 1 where goods_id = #{goodsId} and stock_count > 0")
    int reduceStock(MiaoshaGoods g);
}
