package MaterialMart.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class OrderEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private double totalAmount;
    private String orderType;
    @Column(name = "address")// COD or PICKUP
    private String address;// PENDING, CONFIRMED, DELIVERED
    private String status;
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")  
    private List<OrderItem> orderItems;
}
