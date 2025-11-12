package MaterialMart.Dto;

import lombok.*;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class OrderRequest {
    private Long userId;
    private String orderType;
    private String address;// COD or PICKUP
    private List<OrderItemDto> items;
}
