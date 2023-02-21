package com.wff.goods.entity;

import com.wff.sellergoods.pojo.ItemCat;
import lombok.Data;

import java.util.List;

@Data
public class ItemCatEntity {
    private ItemCat itemCat;
    private List<ItemCat> itemCats;
}
