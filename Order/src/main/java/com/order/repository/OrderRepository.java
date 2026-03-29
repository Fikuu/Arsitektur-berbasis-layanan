package com.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.order.model.orderModel;

public interface OrderRepository extends JpaRepository<orderModel, Long> {

}