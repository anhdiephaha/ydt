package com.ydt.controller;

import com.ydt.entity.Order;
import com.ydt.payload.Payload;
import com.ydt.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private OrderRepository orderRepository;

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@Valid @RequestBody Order order) {
        Payload msg = new Payload();
        try {
            orderRepository.save(order);
        }catch (Exception e){
            logger.error("Tạo đơn hàng thất bại !");
            msg = new Payload("False","Tạo đơn hàng thất bại",null);
        }
        msg.setStatus("true");
        msg.setMesssage("Tạo đơn hàng thành công !");
        msg.setData(null);
        return ResponseEntity.ok(msg);
    }
}
