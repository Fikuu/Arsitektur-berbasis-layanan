package com.order.vo;

import com.order.model.orderModel;

public class Responsetemplate {

    orderModel order;
    Produk produk;

    public orderModel getOrder() {
        return order;
    }

    public void setOrder(orderModel order) {
        this.order = order;
    }

    public Produk getProduk() {
        return produk;
    }

    public void setProduk(Produk produk) {
        this.produk = produk;
    }

}
