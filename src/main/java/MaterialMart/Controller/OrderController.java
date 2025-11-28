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

    public OrderController(OrderService svc) {
        this.svc = svc;
    }

    @PostMapping
    public ResponseEntity<?> place(@RequestBody OrderRequest req) {

        System.out.println("🔥 [ORDER] Incoming order request");
        System.out.println("🔥 [ORDER] User ID : " + req.getUserId());
        System.out.println("🔥 [ORDER] Address : " + req.getAddress());

        try {
            OrderEntity saved = svc.placeOrder(req);
            System.out.println("✅ [ORDER] Saved Successfully (ID = " + saved.getId() + ")");
            return ResponseEntity.ok(saved);

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("❌ [ORDER ERROR] " + ex.getMessage());

            return ResponseEntity
                    .status(500)
                    .body("Order failed: " + ex.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderEntity>> byUser(@PathVariable Long userId) {
        return ResponseEntity.ok(svc.getOrdersByUser(userId));
    }

    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<OrderEntity> cancelOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(svc.cancelOrder(orderId));
    }

    @GetMapping
    public ResponseEntity<List<OrderEntity>> all() {
        return ResponseEntity.ok(svc.getAllOrders());
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<OrderEntity> updateStatus(
            @PathVariable Long orderId,
            @RequestParam String status) {
        return ResponseEntity.ok(svc.updateStatus(orderId, status));
    }
}
