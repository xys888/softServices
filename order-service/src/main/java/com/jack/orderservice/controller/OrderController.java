package com.jack.orderservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    @RequestMapping("/query")
    public String query() {
        System.out.println("i am be visited...");
        return "query order success. ";
    }
}