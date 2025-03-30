package gdu.admin_service.dto.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class OrderDto {
    private Short orderId;
    private String status;
    private Double totalPrice;
    private LocalDateTime orderTime;
}
