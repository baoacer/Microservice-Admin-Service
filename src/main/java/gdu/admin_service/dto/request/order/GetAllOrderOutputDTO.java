package gdu.admin_service.dto.request.order;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class GetAllOrderOutputDTO {
    private short id;
    private String status;
    private LocalDateTime orderDate;
    private String address;
    private List<GetAllOrderItemOutputDTO> listOrderItems;
    private Double totalAmount;
}
