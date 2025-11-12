package MaterialMart.Dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class OrderItemDto {
    private Long productId;
    private int quantity;
}

