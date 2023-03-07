package com.wff.sellergoods.entity;

import com.wff.sellergoods.pojo.Goods;
import com.wff.sellergoods.pojo.GoodsDesc;
import com.wff.sellergoods.pojo.Item;
import lombok.Data;

import java.util.List;

@Data
public class GoodEntity {
    //SPU
    private Goods goods;
    //SKU
    private List<Item> items;
    //DESC
    private GoodsDesc goodsDesc;

    public List<Item> getItemList() {
        return this.items;
    }
}
