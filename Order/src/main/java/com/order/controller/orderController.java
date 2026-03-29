package com.order.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.order.services.OrderServices;
import com.order.vo.Responsetemplate;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.order.model.orderModel;

@RestController
@RequestMapping("/orders")
public class orderController {

    private final OrderServices orderServices;

    public orderController(OrderServices orderServices) {
        this.orderServices = orderServices;
    }

    // Create
    @PostMapping
    public orderModel createOrder(@RequestBody orderModel order) {
        return orderServices.createOrder(order);
    }

    // READ ALL
    @GetMapping
    public List<orderModel> getAllOrders() {
        return orderServices.getAllOrders();
    }

    // READ BY ID
    @GetMapping("/{id}")
    public orderModel getOrderById(@PathVariable Long id) {
        return orderServices.getOrderById(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    public orderModel updateOrder(
            @PathVariable Long id,
            @RequestBody orderModel updatedOrder) {
        return orderServices.updateOrder(id, updatedOrder);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String deleteOrder(@PathVariable Long id) {
        orderServices.deleteOrder(id);
        return "Order deleted successfully";
    }

    // ================= JOIN ORDER + PRODUK =================
    @GetMapping("/produk/{id}")
    public List<Responsetemplate> getOrderWithProdukById(@PathVariable Long id) {
        return orderServices.getOrderWithProdukById(id);
    }

    // semua order dengan produk
    @GetMapping("/produk")
    public List<Responsetemplate> getAllOrdersWithProduk() {
        return orderServices.getAllOrdersWithProduk();

}
