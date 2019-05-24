package com.hmx.e3_item_web.poto;

import com.hmx.e3_pojo.TbItem;

/**
 * 对Tbitem类的拓展
 */
public class Item extends TbItem {

    private String[] images;
    public Item(TbItem tbItem) {
        this.setId(tbItem.getId());
        this.setTitle(tbItem.getTitle());
        this.setPrice(tbItem.getPrice());
        this.setBarcode(tbItem.getBarcode());
        this.setSellPoint(tbItem.getSellPoint());
        this.setCid(tbItem.getCid());
        this.setStatus(tbItem.getStatus());
        this.setCreated(tbItem.getCreated());
        this.setUpdated(tbItem.getUpdated());
        this.setImage(tbItem.getImage());
        this.images = this.getImages();
    }

    public String[] getImages() {
        if (this.getImage()!=null&&!"".equals(this.getImage())) {
            String[] images = this.getImage().split(",");
            return images;
        } else {
            return null;
        }
    }
}
