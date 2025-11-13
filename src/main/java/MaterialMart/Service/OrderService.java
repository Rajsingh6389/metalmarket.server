package MaterialMart.Service;


import MaterialMart.Dto.OrderRequest;
import MaterialMart.Model.OrderEntity;

import java.util.List;

public interface OrderService {
    OrderEntity placeOrder(OrderRequest req);
    List<OrderEntity> getOrdersByUser(Long userId);
    List<OrderEntity> getAllOrders();
    OrderEntity updateStatus(Long orderId, String status);
    OrderEntity cancelOrder(Long orderId);//
}