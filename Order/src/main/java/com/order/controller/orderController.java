package com.order.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import com.order.model.orderModel;
import com.order.services.OrderServices;
import com.order.vo.Responsetemplate;

@RestController
@RequestMapping("/orders")
public class orderController {

    private static final Logger log = LoggerFactory.getLogger(orderController.class);

    private final OrderServices orderServices;

    public orderController(OrderServices orderServices) {
        this.orderServices = orderServices;
    }

    // =====================================================
    // CREATE ORDER
    // =====================================================
    @PostMapping
    public orderModel createOrder(@RequestBody orderModel order) {

        log.info(
                "CREATE_ORDER | STATUS=REQUEST | productId={} | jumlah={} | tanggal={} | total={}",
                order.getId(),
                order.getProdukId(),
                order.getJumlah(),
                order.getTanggal(),
                order.getTotal());

        orderModel savedOrder = orderServices.createOrder(order);

        log.info(
                "CREATE_ORDER | STATUS=SUCCESS | orderId={} | productId={} | jumlah={} | tanggal={} | total={}",
                savedOrder.getId(),
                savedOrder.getProdukId(),
                savedOrder.getJumlah(),
                savedOrder.getTanggal(),
                savedOrder.getTotal());

        return savedOrder;
    }

    // =====================================================
    // GET ALL ORDERS
    // =====================================================
    @GetMapping
    public List<orderModel> getAllOrders() {

        log.info("GET_ALL_ORDERS | STATUS=REQUEST");

        List<orderModel> orders = orderServices.getAllOrders();

        log.info(
                "GET_ALL_ORDERS | STATUS=SUCCESS | totalOrders={}",
                orders.size());

        return orders;
    }

    // =====================================================
    // GET ORDER BY ID
    // =====================================================
    @GetMapping("/{id}")
    public orderModel getOrderById(@PathVariable Long id) {

        log.info(
                "GET_ORDER | STATUS=REQUEST | orderId={}",
                id);

        orderModel order = orderServices.getOrderById(id);

        log.info(
                "GET_ORDER | STATUS=SUCCESS | orderId={} | productId={}",
                order.getId(),
                order.getProdukId());

        return order;
    }

    // =====================================================
    // UPDATE ORDER
    // =====================================================
    @PutMapping("/{id}")
    public orderModel updateOrder(
            @PathVariable Long id,
            @RequestBody orderModel updatedOrder) {

        log.info(
                "UPDATE_ORDER | STATUS=REQUEST | orderId={} | productId={} | jumlah={} | tanggal={} | total={}",
                id,
                updatedOrder.getProdukId(),
                updatedOrder.getJumlah(),
                updatedOrder.getTanggal(),
                updatedOrder.getTotal());

        orderModel order = orderServices.updateOrder(id, updatedOrder);

        log.info(
                "UPDATE_ORDER | STATUS=SUCCESS | orderId={} | productId={} | jumlah={} | tanggal={} | total={}",
                order.getId(),
                order.getProdukId(),
                order.getJumlah(),
                order.getTanggal(),
                order.getTotal());

        return order;
    }

    // =====================================================
    // DELETE ORDER
    // =====================================================
    @DeleteMapping("/{id}")
    public String deleteOrder(@PathVariable Long id) {

        log.info(
                "DELETE_ORDER | STATUS=REQUEST | orderId={}",
                id);

        orderServices.deleteOrder(id);

        log.info(
                "DELETE_ORDER | STATUS=SUCCESS | orderId={}",
                id);

        return "Order deleted successfully";
    }

    // =====================================================
    // ORDER + PRODUCT
    // =====================================================
    @GetMapping("/produk/{id}")
    public List<Responsetemplate> getOrderWithProdukById(@PathVariable Long id) {

        log.info(
                "GET_ORDER_PRODUCT | STATUS=REQUEST | orderId={}",
                id);

        List<Responsetemplate> response = orderServices.getOrderWithProdukById(id);

        log.info(
                "GET_ORDER_PRODUCT | STATUS=SUCCESS | orderId={} | totalProducts={}",
                id,
                response.size());

        return response;
    }

}