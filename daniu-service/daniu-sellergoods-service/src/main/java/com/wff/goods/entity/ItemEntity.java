package com.wff.goods.entity;

import com.wff.sellergoods.pojo.Goods;
import com.wff.sellergoods.pojo.GoodsDesc;
import com.wff.sellergoods.pojo.Item;
import lombok.Data;

@Data
public class ItemEntity {
    //SPU
    private Goods goods;
    //SKU
    private Item item;
    //DESC
    private GoodsDesc goodsDesc;
}
