package MaterialMart.Controller;

import MaterialMart.Dto.OrderRequest;
import MaterialMart.Model.OrderEntity;
import MaterialMart.Service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService svc;
    public OrderController(OrderService svc) { this.svc = svc; }

    @PostMapping
    public ResponseEntity<OrderEntity> place(@RequestBody OrderRequest req) {
        System.out.println("Address received: " + req.getAddress()); // DEBUG

        return ResponseEntity.ok(svc.placeOrder(req));
    }
    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<OrderEntity> cancelOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(svc.cancelOrder(orderId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderEntity>> byUser(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(svc.getOrdersByUser(userId));
    }

    @GetMapping
    public ResponseEntity<List<OrderEntity>> all() {
        return ResponseEntity.ok(svc.getAllOrders());
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<OrderEntity> updateStatus(@PathVariable Long orderId, @RequestParam String status) {
        return ResponseEntity.ok(svc.updateStatus(orderId, status));
    }
}
