package MaterialMart.Service.impl;

import MaterialMart.Dto.OrderItemDto;
import MaterialMart.Dto.OrderRequest;
import MaterialMart.Model.*;
import MaterialMart.Repository.OrderRepository;
import MaterialMart.Repository.ProductRepository;
import MaterialMart.Service.OrderService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepo;
    private final ProductRepository productRepo;

    public OrderServiceImpl(OrderRepository orderRepo, ProductRepository productRepo) {
        this.orderRepo = orderRepo;
        this.productRepo = productRepo;
    }

    @Override
    public OrderEntity placeOrder(OrderRequest req) {
        List<OrderItem> items = new ArrayList<>();
        double total = 0.0;
        for (OrderItemDto dto : req.getItems()) {
            Product p = productRepo.findById(dto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            if (p.getStock() < dto.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product: " + p.getName());
            }
            // reduce stock
            p.setStock(p.getStock() - dto.getQuantity());
            productRepo.save(p);

            OrderItem oi = new OrderItem();
            oi.setProductId(p.getId());
            oi.setProductName(p.getName());
            oi.setQuantity(dto.getQuantity());
            oi.setPrice(p.getPrice());
            items.add(oi);

            total += p.getPrice() * dto.getQuantity();
        }

        OrderEntity order = new OrderEntity();
        order.setUserId(req.getUserId());
        order.setOrderType(req.getOrderType());
        order.setStatus("PENDING");
        order.setTotalAmount(total);
        order.setAddress(req.getAddress());
        order.setOrderItems(items);
        order.setCreatedAt(java.time.LocalDateTime.now());
        return orderRepo.save(order);
    }
    public OrderEntity cancelOrder(Long orderId) {
        OrderEntity order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));

        order.setStatus("Cancelled");
        return orderRepo.save(order); // ✅ User side will see this status
    }

    @Override
    public List<OrderEntity> getOrdersByUser(Long userId) {
        return orderRepo.findByUserId(userId);
    }

    @Override
    public List<OrderEntity> getAllOrders() {
        return orderRepo.findAll();
    }

    @Override
    public OrderEntity updateStatus(Long orderId, String status) {
        OrderEntity o = orderRepo.findById(orderId).orElseThrow();
        o.setStatus(status);
        return orderRepo.save(o);
    }
}
