package gdu.admin_service.dto.request.order;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GetAllOrderItemOutputDTO {
    private short id;
    private GetProductInfoOutputDTO product;
    private int quantity;
    private Double totalPrice;
}