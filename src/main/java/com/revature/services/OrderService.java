package com.revature.services;

import com.revature.dtos.CreateOrderRequest;
import com.revature.dtos.OrderResponse;
import com.revature.models.Order;
import com.revature.models.Payment;
import com.revature.models.User;
import com.revature.repositories.OrderRepository;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final PaymentService paymentService;

    @Autowired
    public OrderService(OrderRepository orderRepository, UserService userService, PaymentService paymentService) {

        this.orderRepository = orderRepository;
        this.userService = userService;
        this.paymentService = paymentService;
    }

    public OrderResponse createOrder(CreateOrderRequest createOrderRequest) {
        Order newOrder = new Order();
        User foundUser = userService.findUserById(createOrderRequest.getUserId());
        Payment foundPayment = paymentService.findPaymentById(createOrderRequest.getPaymentId());
        newOrder.setUserId(foundUser);
        newOrder.setPaymentId(foundPayment);
        newOrder.setOrderDate(new Date(System.currentTimeMillis()));
        newOrder.setOrderFulfilled(false);
        newOrder.setShipmentAddress(createOrderRequest.getShipmentAddress());

        orderRepository.save(newOrder);

        OrderResponse orderResponse = new OrderResponse(newOrder);

        return orderResponse;
    }

    public List<OrderResponse> findAll() {
        ArrayList<OrderResponse> orderResponses = new ArrayList<>();
        List<Order> orders = orderRepository.findAll();
        for (Order o : orders) {
            OrderResponse orderResponse = new OrderResponse(o);
            orderResponses.add(orderResponse);
        }
        return orderResponses;
    }

    public Optional<Order> findById(int id) {
        return findById(id);
    }

    public Order save(Order order) {
        return orderRepository.save(order);
    }

    public boolean delete(int id) {
        Order foundOrder = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order couldn't be deleted."));
        orderRepository.delete(foundOrder);
        return true;
    }
}
