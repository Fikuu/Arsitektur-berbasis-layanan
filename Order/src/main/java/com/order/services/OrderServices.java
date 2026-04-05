package com.order.services;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import com.order.model.orderModel;
import com.order.repository.OrderRepository;
import com.order.vo.Responsetemplate;
import com.order.vo.Produk;

@Service
public class OrderServices {

    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;
    private final DiscoveryClient discoveryClient;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public OrderServices(OrderRepository orderRepository, RestTemplate restTemplate, DiscoveryClient discoveryClient) {
        this.orderRepository = orderRepository;
        this.restTemplate = restTemplate;
        this.discoveryClient = discoveryClient;
    }

    // public orderModel createOrder(orderModel order) {
    // rabbitTemplate.convertAndSend(queue.getName(), order);
    // return orderRepository.save(order);
    // }

    public orderModel createOrder(orderModel order) {

        // 1. simpan dulu ke database
        orderModel savedOrder = orderRepository.save(order);

        // 2. kirim ke RabbitMQ (pakai JSON)
        rabbitTemplate.convertAndSend("orderQueue", savedOrder);

        System.out.println("ORDER SENT TO QUEUE: " + savedOrder.getId());

        return savedOrder;
    }

    public List<orderModel> getAllOrders() {
        return orderRepository.findAll();
    }

    public orderModel getOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public orderModel updateOrder(Long id, orderModel updatedOrder) {
        orderModel existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        existingOrder.setProdukId(updatedOrder.getProdukId());
        existingOrder.setJumlah(updatedOrder.getJumlah());
        existingOrder.setTanggal(updatedOrder.getTanggal());
        existingOrder.setTotal(updatedOrder.getTotal());
        return orderRepository.save(existingOrder);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    // ================= JOIN ORDER + PRODUK =================
    public List<Responsetemplate> getOrderWithProdukById(Long id) {

        List<Responsetemplate> responseList = new ArrayList<>();

        // ambil order
        orderModel order = getOrderById(id);

        // ambil service PRODUK dari Eureka
        List<ServiceInstance> instances = discoveryClient.getInstances("PRODUCT");

        if (instances.isEmpty()) {
            throw new RuntimeException("Service PRODUK tidak ditemukan di Eureka");
        }

        ServiceInstance serviceInstance = instances.get(0);

        // bentuk URL
        String url = serviceInstance.getUri().toString() + "/product/" + order.getProdukId();

        System.out.println("CALL API PRODUK: " + url);

        // call API
        Produk produk;
        try {
            produk = restTemplate.getForObject(url, Produk.class);
        } catch (Exception e) {
            throw new RuntimeException("Gagal mengambil data produk: " + e.getMessage());
        }

        if (produk == null) {
            throw new RuntimeException("Produk tidak ditemukan");
        }

        // mapping response
        Responsetemplate vo = new Responsetemplate();
        vo.setOrder(order);
        vo.setProduk(produk);

        responseList.add(vo);

        return responseList;
    }
}
