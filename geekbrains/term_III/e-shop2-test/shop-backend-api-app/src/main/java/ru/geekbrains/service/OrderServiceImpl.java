package ru.geekbrains.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import ru.geekbrains.controller.dto.OrderDto;
import ru.geekbrains.controller.dto.OrderLineItemDto;
import ru.geekbrains.persist.OrderRepository;
import ru.geekbrains.persist.ProductRepository;
import ru.geekbrains.persist.UserRepository;
import ru.geekbrains.persist.model.Order;
import ru.geekbrains.persist.model.OrderLineItem;
import ru.geekbrains.persist.model.Product;
import ru.geekbrains.persist.model.User;
import ru.geekbrains.service.dto.OrderMessage;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderRepository orderRepository;

    private final CartService cartService;

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    private final RabbitTemplate rabbitTemplate;

    private final SimpMessagingTemplate template;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            CartService cartService,
                            UserRepository userRepository,
                            ProductRepository productRepository,
                            RabbitTemplate rabbitTemplate,
                            SimpMessagingTemplate template) {
        this.orderRepository = orderRepository;
        this.cartService = cartService;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.rabbitTemplate = rabbitTemplate;
        this.template = template;
    }

    public List<OrderDto> findOrdersByUsername(String username) {
        return orderRepository.findAllByUsername(username).stream()
                .map(o -> new OrderDto(
                        o.getId(),
                        o.getOrderDate(),
                        o.getStatus().name(),
                        o.getUser().getUsername(),
                        o.getOrderLineItems().stream()
                                .map(li -> new OrderLineItemDto(
                                        li.getId(),
                                        li.getOrder().getId(),
                                        li.getProduct().getId(),
                                        li.getProduct().getName(),
                                        li.getPrice(),
                                        li.getQty(),
                                        li.getColor(),
                                        li.getMaterial()
                                )).collect(Collectors.toList())
                )).collect(Collectors.toList());
    }

    @Transactional
    public void createOrder(String username) {
        if (cartService.getLineItems().isEmpty()) {
            logger.info("Can't create order for empty Cart");
            return;
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = orderRepository.save(new Order(
                null,
                LocalDateTime.now(),
                Order.OrderStatus.CREATED,
                user
        ));

        List<OrderLineItem> orderLineItems = cartService.getLineItems()
                .stream()
                .map(li -> new OrderLineItem(
                        null,
                        order,
                        findProductById(li.getProductId()),
                        li.getProductDto().getPrice(),
                        li.getQty(),
                        li.getColor(),
                        li.getMaterial()
                ))
                .collect(Collectors.toList());
        order.setOrderLineItems(orderLineItems);
        orderRepository.save(order);
        rabbitTemplate.convertAndSend("order.exchange", "new_order", new OrderMessage(order.getId(), order.getStatus().name()));
    }

    private Product findProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No product with id"));
    }

    @RabbitListener(queues = "processed.order.queue")
    public void receive(OrderMessage order) {
        logger.info("Order with id '{}' state change to '{}'", order.getId(), order.getState());
        template.convertAndSend("/order_out/order", order);
    }
}
